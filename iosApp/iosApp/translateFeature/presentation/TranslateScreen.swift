import SwiftUI
import shared

struct TranslateScreen: View {
    private var historyDataSource: HistoryDataSource
    private var translateUseCase: Translate
    private let parser: any VoiceToTextParser
    @ObservedObject var viewModel: IOSTranslateViewModel
    
    init(historyDataSource: HistoryDataSource, translateUseCase: Translate, parser: VoiceToTextParser) {
        self.historyDataSource = historyDataSource
        self.translateUseCase = translateUseCase
        self.parser = parser
        self.viewModel = IOSTranslateViewModel(historyDataSource: historyDataSource, translateUseCase: translateUseCase)
    }
    
    var body: some View {
        ZStack {
            List {
                HStack(alignment: .center) {
                    LanguageDropDown(
                        language: viewModel.state.fromLanguage,
                        isOpen: viewModel.state.isChoosingFromLanguage,
                        selectLanguage: { language in
                            viewModel.onEvent(event: TranslateEvent.ChooseFromLanguage(language: language))
                        })
                    Spacer()
                    SwapLanguageButton(onClick: {
                        viewModel.onEvent(event: TranslateEvent.SwapLanguages())
                    })
                    Spacer()
                    LanguageDropDown(
                        language: viewModel.state.toLanguage,
                        isOpen: viewModel.state.isChoosingToLanguage,
                        selectLanguage: { language in
                            viewModel.onEvent(event: TranslateEvent.ChooseToLanguage(language: language))
                        })
                }
                .listRowBackground(Color.background)
                .listRowSeparator(.hidden)
                
                TranslateTextField(
                    fromText: Binding(
                        get: { viewModel.state.fromText },
                        set: { viewModel.onEvent(event: TranslateEvent.ChangeTranslationText(text: $0)) }
                    ),
                    toText: viewModel.state.toText,
                    isTranslating: viewModel.state.isTranslating,
                    fromLanguage: viewModel.state.fromLanguage,
                    toLanguage: viewModel.state.toLanguage,
                    onTranslateEvent: { event in
                        viewModel.onEvent(event: event)
                    })
                .listRowBackground(Color.background)
                .listRowSeparator(.hidden)
                
                if !viewModel.state.history.isEmpty {
                    Text("History")
                        .font(.title)
                        .bold()
                        .frame(maxWidth: .infinity, alignment: .leading)
                        .listRowBackground(Color.background)
                        .listRowSeparator(.hidden)
                }
                
                ForEach(viewModel.state.history, id: \.self.id) { item in
                    TranslateHistoryItem(
                        item: item,
                        onCLick: {
                            viewModel.onEvent(event: TranslateEvent.SelectHistoryItem(item: item))
                        })
                    .listRowBackground(Color.background)
                    .listRowSeparator(.hidden)
                }
                
            }
            .listStyle(.plain)
            .buttonStyle(.plain)
            
            VStack {
                Spacer()
                NavigationLink(
                    destination: VoiceToTextScreen(onResult: {spokenText in
                        viewModel.onEvent(event: TranslateEvent.SubmitVoiceResult(result: spokenText))
                    }, parser: parser, languageCode: viewModel.state.fromLanguage.language.langCode)) {
                        ZStack {
                            Circle()
                                .foregroundColor(.primaryColor)
                                .padding()
                            Image(uiImage: UIImage(named: "mic")!)
                                .foregroundColor(.onPrimary)
                                .accessibilityIdentifier("Record audio")
                        }
                        .frame(maxWidth: 100, maxHeight: 100)
                    }
            }
        }
        .onAppear {
            viewModel.startObserving()
        }
        .onDisappear {
            viewModel.dispose()
        }
    }
}
