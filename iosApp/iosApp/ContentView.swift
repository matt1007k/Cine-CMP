import UIKit
import SwiftUI
import ComposeApp

struct ComposeView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        MainViewControllerKt.MainViewController(nativeViewFactory: IOSButtonFactory.shared)
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

struct ContentView: View {
    var body: some View {
//        ChatSocketView()
        ComposeView()
            .ignoresSafeArea(.all)
            .ignoresSafeArea(.keyboard)
        // Compose has own keyboard handler
    }
}
