import SwiftUI
import common

func greet() -> String {
    return "Hello \(PlatformKt.getPlatformName())"
}

struct ContentView: View {
    var body: some View {
        Text(greet())
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
