//
//  IOSButton.swift
//  iosApp
//
//  Created by Max Meza on 6/15/25.
//

import SwiftUI
import ComposeApp
import MapKit

class IOSButtonFactory: NativeViewFactory {
    static var shared = IOSButtonFactory()
    func createButtonView(label: String, onClick: @escaping () -> Void) -> UIViewController {
        let view = AnnotatedMapView()
           
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
        .buttonStyle(.borderedProminent)
        .ignoresSafeArea(.all)
    }
}

#Preview {
    IOSButton(label: "Hello", action: {})
}

struct AnnotatedMapView: View {
    // Manages map region state
    @State private var region = MKCoordinateRegion(
        center: CLLocationCoordinate2D(latitude: 51.5074, longitude: -0.1278),
        span: MKCoordinateSpan(latitudeDelta: 0.1, longitudeDelta: 0.1)
    )
    // Displays a map with a custom annotation
    var body: some View {
        Map(coordinateRegion: $region, annotationItems: [Landmark.example]) { landmark in
            MapMarker(coordinate: landmark.coordinate, tint: .blue)
        }
//        .frame(width: 300, height: 300)
    }
}

struct Landmark: Identifiable {
    let id = UUID()
    let name: String
    let coordinate: CLLocationCoordinate2D

    static let example = Landmark(
        name: "Big Ben",
        coordinate: CLLocationCoordinate2D(latitude: 51.5007, longitude: -0.1246)
    )
}
