//
//  ChatSocketView.swift
//  iosApp
//
//  Created by Max Meza on 7/16/25.
//

import SwiftUI
import Foundation // For URLSessionWebSocketTask

struct ChatSocketView: View {
    @StateObject var client = WebSocketClient(roomId: "general") // Connect to the "general" room
    @State private var messageInput: String = ""

    var body: some View {
        VStack {
            Text(client.isConnected ? "Connected to \(client.roomId)" : "Disconnected")
                .foregroundColor(client.isConnected ? .green : .red)
            if let error = client.connectionError {
                Text("Error: \(error)")
                    .foregroundColor(.red)
            }

            ScrollView {
                VStack(alignment: .leading) {
                    ForEach(client.messages, id: \.self) { message in
                        Text(message)
                    }
                }
                .frame(maxWidth: .infinity, alignment: .leading)
                .padding()
            }
            .frame(maxWidth: .infinity, maxHeight: .infinity)
            .background(Color.gray.opacity(0.1))
            .cornerRadius(10)
            .padding()

            HStack {
                TextField("Type a message", text: $messageInput)
                    .textFieldStyle(RoundedBorderTextFieldStyle())
                    .padding(.horizontal)

                Button("Send") {
                    if !messageInput.isEmpty {
                        client.sendMessage(messageInput)
                        messageInput = ""
                    }
                }
                .buttonStyle(.borderedProminent)
                .padding(.trailing)
            }

            Button(client.isConnected ? "Disconnect" : "Connect") {
                client.disconnect()
                if client.isConnected {
                    client.disconnect()
                } else {
                    client.connect()
                }
            }
            .buttonStyle(.borderless)
            .padding()
        }
        .navigationTitle("WebSocket Chat")
        .onDisappear {
            client.disconnect() // Ensure disconnection when view disappears
        }
    }
}

#Preview {
    ChatSocketView()
}


class WebSocketClient: NSObject, ObservableObject {
    @Published var messages: [String] = []
    @Published var isConnected: Bool = false
    @Published var connectionError: String?

    private var webSocketTask: URLSessionWebSocketTask?
    private var reconnectAttempt = 0
    private let maxReconnectAttempts = 10
    private let reconnectDelayBase: TimeInterval = 1 // Initial delay in seconds
    private var reconnectTimer: Timer? // Using Timer for robust scheduling

    let roomId: String

    init(roomId: String) {
        self.roomId = roomId
        super.init()
        print("WebSocketClient initialized for room: \(roomId)")
        connect() // Initial connection attempt
    }

    // Ensure resources are cleaned up when the object is deallocated
    deinit {
        print("WebSocketClient for room \(roomId) is being deinitialized.")
        disconnect()
    }

    func connect() {
        print("Attempting to connect to WebSocket for room: \(roomId). Current reconnect attempt: \(reconnectAttempt)")

        // Invalidate any existing reconnect timer when a new connection is explicitly started
        reconnectTimer?.invalidate()
        reconnectTimer = nil

        // Clear previous connection state before initiating a new one
        webSocketTask?.cancel() // Ensure any previous task is cancelled
        webSocketTask = nil // Clear the old task

        guard let url = URL(string: "ws://localhost:8080/ws/\(roomId)") else {
            DispatchQueue.main.async {
                self.connectionError = "Invalid URL"
                print("Error: Invalid URL for WebSocket connection.")
            }
            return
        }

        let session = URLSession(configuration: .default, delegate: self, delegateQueue: .main)
        webSocketTask = session.webSocketTask(with: url)
        webSocketTask?.resume() // This starts the connection process

        // It's important to start listening *after* the task is resumed
        listenForMessages()
    }

    private func listenForMessages() {
        // Ensure the task exists and is not nil before attempting to receive
        guard let webSocketTask = webSocketTask else {
            print("Listen for messages called but webSocketTask is nil.")
            return
        }

        webSocketTask.receive { [weak self] result in
            guard let self = self else { return }

            switch result {
            case .failure(let error):
                print("WebSocket receive error for room \(self.roomId): \(error.localizedDescription)")
                // Do NOT call attemptReconnect() here. Let didCloseWith handle the main disconnect logic.
                // However, update UI state immediately.
                DispatchQueue.main.async {
                    self.isConnected = false
                    self.connectionError = error.localizedDescription
                }
                attemptReconnect()
            case .success(let message):
                switch message {
                case .string(let text):
                    DispatchQueue.main.async {
                        self.messages.append("Received: \(text)")
                    }
                case .data(let data):
                    if let string = String(data: data, encoding: .utf8) {
                        DispatchQueue.main.async {
                            self.messages.append("Received Binary: \(string)")
                        }
                    }
                @unknown default:
                    print("Received unknown WebSocket message type.")
                    break
                }
                // Crucially, call listenForMessages() again to keep receiving the next message
                self.listenForMessages()
            }
        }
    }

    func sendMessage(_ message: String) {
        guard isConnected, let webSocketTask = webSocketTask else {
            print("Not connected to WebSocket for room \(roomId). Message not sent: \(message)")
            return
        }

        let wsMessage = URLSessionWebSocketTask.Message.string(message)
        webSocketTask.send(wsMessage) { error in
            if let error = error {
                print("Error sending message for room \(self.roomId): \(error.localizedDescription)")
                // If sending fails, it might indicate a lost connection.
                // The didCloseWith delegate method should eventually fire and handle reconnect.
            } else {
                DispatchQueue.main.async {
                    self.messages.append("Sent: \(message)")
                }
            }
        }
    }

    func disconnect() {
        print("Explicitly disconnecting WebSocket for room: \(roomId).")
        // Use a specific close code for intentional disconnects
        webSocketTask?.cancel(with: .goingAway, reason: "Client requested disconnect".data(using: .utf8))
        webSocketTask = nil // Clear the task immediately
        reconnectTimer?.invalidate() // Stop any pending reconnect attempts
        reconnectTimer = nil
        DispatchQueue.main.async {
            self.isConnected = false
            self.connectionError = nil
        }
        self.reconnectAttempt = 0 // Reset reconnect attempts on intentional disconnect
    }

    private func attemptReconnect() {
        // Prevent multiple reconnect timers from running simultaneously
        reconnectTimer?.invalidate()
        reconnectTimer = nil

        guard reconnectAttempt < maxReconnectAttempts else {
            DispatchQueue.main.async {
                self.connectionError = "Max reconnect attempts reached. Please check server or network."
            }
            print("Max reconnect attempts reached for room \(roomId). Stopping.")
            return
        }

        // Calculate exponential backoff delay
        let delay = reconnectDelayBase * pow(2.0, Double(reconnectAttempt))
        print("Scheduling reconnect attempt \(reconnectAttempt + 1)/\(maxReconnectAttempts) for room \(roomId) in \(String(format: "%.1f", delay)) seconds...")

        // Schedule the reconnect attempt using Timer on the main run loop
        reconnectTimer = Timer.scheduledTimer(withTimeInterval: delay, repeats: false) { [weak self] _ in
            guard let self = self else { return }
            print("Executing reconnect attempt \(self.reconnectAttempt + 1) for room \(self.roomId)...")
            self.reconnectAttempt += 1 // Increment attempt *before* connecting
            self.connect() // Call connect to try establishing a new connection
        }
    }
}

// MARK: - URLSessionWebSocketDelegate
extension WebSocketClient: URLSessionWebSocketDelegate {
    func urlSession(_ session: URLSession, webSocketTask: URLSessionWebSocketTask, didOpenWithProtocol protocol: String?) {
        print("WebSocket for room \(roomId) did open with protocol: \(`protocol` ?? "none")")
        DispatchQueue.main.async {
            self.isConnected = true
            self.connectionError = nil
            self.reconnectAttempt = 0 // Reset on successful connection
        }
        // Crucial: Invalidate timer if a reconnection was pending and now succeeded
        reconnectTimer?.invalidate()
        reconnectTimer = nil
    }

    func urlSession(_ session: URLSession, webSocketTask: URLSessionWebSocketTask, didCloseWith closeCode: URLSessionWebSocketTask.CloseCode, reason: Data?) {
        let reasonString = reason != nil ? String(data: reason!, encoding: .utf8) : "No reason"
        print("WebSocket for room \(roomId) did close with code: \(closeCode.rawValue) (\(closeCode)), reason: \(reasonString ?? "")")

        // Always update connection status on the main thread
        DispatchQueue.main.async {
            self.isConnected = false

            // Define what constitutes a "clean" close that *shouldn't* trigger a reconnect
            let isCleanClose: Bool
            switch closeCode {
            case .normalClosure, .goingAway:
                isCleanClose = true
            // If your server sends specific custom codes for clean disconnects (e.g., user logout)
            // case .invalid where closeCode.rawValue == 4000: // Example custom clean code
            //    isCleanClose = true
            default:
                isCleanClose = false
            }

            if !isCleanClose {
                print("WebSocket for room \(self.roomId) closed uncleanly. Attempting reconnect.")
                self.attemptReconnect()
            } else {
                self.connectionError = "Disconnected: \(reasonString ?? "Clean close")"
                print("WebSocket for room \(self.roomId) closed cleanly. No reconnect initiated.")
            }
        }
        // CRUCIAL: Clear the old webSocketTask when it closes
        self.webSocketTask = nil
    }
}
