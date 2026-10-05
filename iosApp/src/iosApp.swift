import KotlinModules
import SwiftUI

struct ComposeView: UIViewControllerRepresentable {
  func makeUIViewController(context: Context) -> some UIViewController {
    ViewControllerKt.createCalMeViewController()
  }
  func updateUIViewController(_ uiViewController: UIViewControllerType, context: Context) {}
}

struct ContentView: View {
  var body: some View {
    ComposeView().ignoresSafeArea()
  }
}

@main
struct CalMeDemoApp: App {
  var body: some Scene {
    WindowGroup {
      ContentView()
    }
  }
}
