import SwiftUI
import shared

struct LanguageDisplay: View {
    var language: UiLanguage
    
    var body: some View {
        HStack {
            SmallLanguageIcon(language: language)
                .padding(.trailing, 5)
            Text(language.language.langName)
                .foregroundColor(Color.lightBlue)
        }
    }
}

struct LanguageDisplay_Previews: PreviewProvider {
    static var previews: some View {
        LanguageDisplay(
            language: UiLanguage(language: Language.german, imageName: "german"))
    }
}
