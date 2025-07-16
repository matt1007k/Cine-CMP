//
//  ChatView.swift
//  iosApp
//
//  Created by Max Meza on 7/4/25.
//

import SwiftUI
import SocketIO

class SocketIOClientManager: ObservableObject {
    let manager = SocketManager(socketURL: URL(string: "ws://localhost:8080")!, config: [.log(true), .compress])
    let socket: SocketIO.SocketIOClient
    
    init() {
        socket = self.manager.defaultSocket
        
        connect()
        
//        socket.on("sendMessage") {data, ack in
//            print("Message \(data.first["message"])")
//            guard let cur = data[0] as? Double else { return }
//            
//            self.socket.emitWithAck("canUpdate", cur).timingOut(after: 0) {data in
//                if data.first as? String ?? "passed" == SocketAckStatus.noAck {
//                    // Handle ack timeout
//                }
//
//                self.socket.emit("update", ["amount": cur + 2.50])
//            }
//
//            ack.with("Got your currentAmount", "dude")
//        }
        
        socket.connect()
    }
    
    func connect() {
        socket.on(clientEvent: .connect) { data, ack in
            print("socket connected")
            
            self.socket.emit("join", "room")
        }
    }
    
    func emit(event: String, data: [String: Any]) {
        socket.emit(event, data)
    }
    
    func on(event: String, _ callback: @escaping ([Any], SocketAckEmitter) -> Void) {
        socket.on(event) { data, ack in
            callback(data, ack)
        }
    }
    
    func disconnect() {
        socket.on(clientEvent: .disconnect) { data, ack in
            print("socket disconnected")
        }
    }
}

struct ChatView: View {
    @State private var message: String = ""
    @State private var messages: [String] = ["Hello, World!"]
    
    @ObservedObject var socket = SocketIOClientManager()
    
    init() {
        socket.on(event: "join_user") { data, ack in
            print("Joined: \(data)")
        }
    }
    
    var body: some View {
        NavigationStack {
            List(messages, id: \.self) { message in
                Text(message)
            }
            .safeAreaInset(edge: .bottom) {
                HStack {
                    TextField("Message", text: $message)
                        .frame(minWidth: 0, maxWidth: .infinity)
                        .textFieldStyle(.roundedBorder)
                    Button {
                        sendMessage()
                    } label: {
                        Image(systemName: "envelope.fill")
                    }
                    .buttonStyle(.borderless)
                    .disabled(message.isEmpty)
                }
                .frame(minWidth: 0, maxWidth: .infinity)
                
                              .padding()
            }
            
        }
        .navigationTitle("Chat")
        .toolbarTitleDisplayMode(.inline)
        .task {
            DispatchQueue.main.asyncAfter(deadline: .now() + 2) {
                self.messages.append("test")
            }
            socket.socket.on("sendMessage") { [self] (data, ack) in
                if let data = data.first as? [String: Any] {
                    print("Message ChatVview: \(data["message"]!)")
                    DispatchQueue.main.async {
                        self.messages.append(data["message"] as! String)
                    }
                    
                }
            }
            
        }
        .onDisappear {
            socket.disconnect()
        }
    }
    
    func sendMessage() {
        socket.emit(event: "sendMessage", data: ["message": message])
        self.message = ""
    }
}

#Preview {
    ChatView()
}
