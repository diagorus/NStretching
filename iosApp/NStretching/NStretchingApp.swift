import FirebaseCore
import SwiftUI
import sharedKit

class AppDelegate: NSObject, UIApplicationDelegate {

    func application(
        _ application: UIApplication,
        didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]? = nil
    ) -> Bool {
        FirebaseApp.configure()
        return true
    }
}

@main
struct NStretchingApp: App {

    @UIApplicationDelegateAdaptor(AppDelegate.self) var delegate
    
    init() {
        KoinInitKt.doInitKoin()

        // NotificationCenter.default.addObserver(self, selector: #selector(localeDidChange), name: NSLocale.currentLocaleDidChangeNotification, object: nil)
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
