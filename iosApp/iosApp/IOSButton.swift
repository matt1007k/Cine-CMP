//
//  IOSButton.swift
//  iosApp
//
//  Created by Max Meza on 6/15/25.
//

import SwiftUI
import ComposeApp

class IOSButtonFactory: NativeViewFactory {
    static var shared = IOSButtonFactory()
    func createButtonView(label: String, onClick: @escaping () -> Void) -> UIViewController {
        let view = IOSButton(label: label, action: onClick)
           
        return UIHostingController(rootView: view)
    }
}

struct IOSButton: View {
    var label: String
    var action: () -> Void
    
    var body: some View {
        Button(action: action) {
            Text(label)
        }
        .buttonStyle(.bordered)
    }
}

#Preview {
    IOSButton(label: "Hello", action: {})
}
