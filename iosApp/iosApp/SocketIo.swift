//
//  SocketIo.swift
//  iosApp
//
//  Created by Max Meza on 7/6/25.
//
import SocketIO
import Foundation
import UIKit

@objc
public class SocketIo: NSObject {
    private let socketManager: SocketManager
    private let socket: SocketIOClient

    @objc
    public init(
        endpoint: String,
        queryParams: [String: Any]?,
//        transport: SocketIoTransport = .undefined
    ) {
        // Configuration setup
        var configuration: SocketIOClientConfiguration = [ .compress, .log(true) ]

        // Handle query parameters
        if let queryParams = queryParams {
            configuration.insert(.connectParams(queryParams))
        }

        // Transport mode configuration
//        switch transport {
//        case .websocket:
//            configuration.insert(.forceWebsockets(true))
//        case .polling:
//            configuration.insert(.forcePolling(true))
//        case .undefined: do {}
//        }

        // Initialize Socket.IO manager and client
        socketManager = SocketManager(
            socketURL: URL(string: endpoint)!,
            config: configuration
        )
        socket = socketManager.defaultSocket
    }
}

//@objc
//public enum SocketIoTransport: String {
//    case websocket = "websocket"
//    case polling = "polling"
//    case undefined = "undefined"
//}
