//
//  ToastCore.swift
//  iosApp
//
//  Created by Jonas Hiltl on 05.11.22.
//  Copyright © 2022 orgName. All rights reserved.
//

import ComposableArchitecture
import shared
import SwiftUI

extension shared.Toast {
    var background: Color {
        switch variant {
        case shared.ToastVariant.error:
            return Color("ErrorBG")
        case shared.ToastVariant.warning:
            return Color("WarningGB")
        case shared.ToastVariant.success:
            return Color("SuccessBG")
        default:
            return Color("ErrorBG")
        }
    }
    
    var border: Color {
        switch variant {
        case shared.ToastVariant.error:
            return Color("ErrorBorder")
        case shared.ToastVariant.warning:
            return Color("WarningBorder")
        case shared.ToastVariant.success:
            return Color("SuccessBorder")
        default:
            return Color("ErrorBorder")
        }
    }
    
    var icon: String {
        switch variant {
        case shared.ToastVariant.error:
            return "xmark.circle.fill"
        case shared.ToastVariant.warning:
            return "exclamationmark.triangle.fill"
        case shared.ToastVariant.success:
            return "checkmark.circle.fill"
        default:
            return ""
        }
    }

}

typealias ToastStore = StoreOf<ToastCore>
typealias ToastViewStore = ViewStoreOf<AppCore>

struct ToastCore: ReducerProtocol {
    struct State: Equatable {
        var currentToast: Toast?
    }
    
    enum Action: Equatable {
        case consume
        case setToast(Toast?)
        case showToast(Toast?)
    }
    
    @Dependency(\.toastQueue) var queue
    @Dependency(\.continuousClock) var clock
    
    func reduce(into state: inout State, action: Action) -> EffectTask<Action> {
        switch action {
        case let .setToast(toast):
            state.currentToast = nil
            state.currentToast = toast
            return .none
        case let .showToast(toast):
            return .run { send in
                await send(.setToast(toast))
                try await clock.sleep(for: .seconds(3))
                await send(.setToast(nil))
            }
        case .consume:
            return .run { send in
                for try await toast in queue.consume() {
                    await send(.showToast(toast))
                }
            }
        }
    }
}
