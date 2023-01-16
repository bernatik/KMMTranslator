import SwiftUI
import shared

struct VoiceToTextScreen: View {
    private var onResult: (String) -> Void
    
    @ObservedObject var viewModel: IOSVoiceToTextViewModel
    private let parser: any VoiceToTextParser
    private let languageCode: String
    
    @Environment(\.presentationMode) var presentation
    
    init(onResult: @escaping (String) -> Void,
         parser: any VoiceToTextParser,
         languageCode: String) {
        self.onResult = onResult
        self.viewModel = IOSVoiceToTextViewModel(parser: parser, languageCode: languageCode)
        self.parser = parser
        self.languageCode = languageCode
    }
    
    var body: some View {
        VStack {
            Spacer()
            mainView
            Spacer()
            HStack {
                Spacer()
                VoiceRecorderButton(
                    displayState: viewModel.state.displayState ?? .waitingToTalk,
                    onClick: {
                        if viewModel.state.displayState != .displayingResults {
                            viewModel.onEvent(event: VoiceToTextEvent.ToggleRecording(languageCode: languageCode))
                        } else {
                            onResult(viewModel.state.spokenString)
                            self.presentation.wrappedValue.dismiss()
                        }
                        
                    })
                if viewModel.state.displayState == .displayingResults {
                    Button(action: {
                        viewModel.onEvent(event: VoiceToTextEvent.ToggleRecording(languageCode: languageCode))
                    }) {
                        Image(systemName: "arrow.clockwise")
                            .foregroundColor(.lightBlue)
                    }
                }
                Spacer()
            }
        }
        .onAppear {
            viewModel.startObserving()
        }
        .onDisappear {
            viewModel.dispose()
        }
        .background(Color.background)
    }
    
    var mainView: some View {
        if let displayState = viewModel.state.displayState {
            switch displayState {
            case .waitingToTalk:
                return AnyView(
                    Text("Click record and start talking.")
                        .font(.title2))
            case .displayingResults:
                return AnyView(Text(viewModel.state.spokenString)
                    .font(.title2))
            case .error:
                return AnyView(Text(viewModel.state.recordError ?? "Unknown error")
                    .font(.title2)
                    .foregroundColor(.red))
            case .speaking:
                return AnyView(VoiceRecordDisplay(powerRatios: viewModel.state.powerRatios.map { Double($0)})
                    .frame(maxHeight: 100)
                    .padding())
            default: return AnyView(EmptyView())
            }
        } else {
            return AnyView(EmptyView())
        }
    }
    
}
