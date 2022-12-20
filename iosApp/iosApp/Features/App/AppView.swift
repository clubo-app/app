//
//  AppView.swift
//  iosApp
//
//  Created by Jonas Hiltl on 07.11.22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import SwiftUINavigation
import ComposableArchitecture
import shared

struct AppView: View {
    let store: AppStore
    @ObservedObject var viewStore: AppViewStore
   
    init(store: AppStore) {
        self.store = store
        self.viewStore = ViewStore(store)
        self.viewStore.send(.onInit)
    }
    
    var body: some View {
        ZStack {
            MainNavigationView(store: self.store.scope(state: \.authenticated, action: AppCore.Action.authenticated))
                .sheet(unwrapping: viewStore.binding(get: \.unauthenticated, send: .dismissAuthentication)) { _ in
                    IfLetStore(self.store.scope(state: \.unauthenticated, action: AppCore.Action.unauthenticated)) { store in
                        OnboardingView(store: store)
                    }
                }
            ToastView(store: self.store.scope(state: \.toast, action: AppCore.Action.toast))
        }
        .task {
            viewStore.send(.consumeAuthChanges)
        }
    }
}

struct AppView_Previews: PreviewProvider {
    static var previews: some View {
        AppView(
            store: AppStore(initialState: AppCore.State(), reducer: AppCore())
        )
    }
}
