//
//  OnboardingView.swift
//  iosApp
//
//  Created by Jonas Hiltl on 07.11.22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import ComposableArchitecture
import SwiftUINavigation

struct OnboardingView: View {
    let store: OnboardingStore
    
    var body: some View {
        WithViewStore(self.store) { viewStore in
            NavigationStack {
                VStack(alignment: .leading) {
                    Text("Start by choosing your role.")
                        .caption()
                    
                    Spacer()
                    
                    VStack(spacing: Padding.l) {
                        ChooseButton(
                            store: self.store,
                            image: "party.popper",
                            title: "I'm a Partyer",
                            subtitle: "Find the hottest parties wherever you are or create your own Parties.",
                            disabled: false,
                            value: .signup
                        )
                        ChooseButton(
                            store: self.store,
                            image: "wineglass",
                            title: "I'm an Organiser",
                            subtitle: "Coming soon...",
                            disabled: true,
                            value: .companySignup
                        )
                    }
                    
                    Spacer()
                    
                    HStack(alignment:.center) {
                        Text("Already have an Account?")
                        Button {
                            viewStore.send(.updateRoute(.login))
                        } label: {
                            Text("Log in")
                        }
                    }
                    .frame(maxWidth: .infinity)
                    
                }
                .padding(Padding.l)
                .background(Color("Background").ignoresSafeArea())
                .toolbar() {
                    ToolbarItem(placement: .automatic) {
                        Button("Skip", action: {
                            viewStore.send(.skipAuth)
                        })
                        .textButton()
                    }
                }
                .navigationTitle("Get Started")
                .navigationDestination(
                    unwrapping: viewStore.binding(get: \.route, send: OnboardingCore.Action.updateRoute),
                    case: /OnboardingCore.State.Route.signup
                ) { _ in
                    IfLetStore(self.store.scope(state: \.signupState, action: OnboardingCore.Action.signup)) { store in
                       SignupView(store: store)
                    }
                }
                .navigationDestination(
                    unwrapping: viewStore.binding(get: \.route, send: OnboardingCore.Action.updateRoute),
                    case: /OnboardingCore.State.Route.login
                ) { _ in
                    IfLetStore(self.store.scope(state: \.loginState, action: OnboardingCore.Action.login)) { store in
                       LoginView(store: store)
                    }
                }
            }
        }
    }
}

struct ChooseButton: View {
    let store: StoreOf<OnboardingCore>
    let image: String
    let title: String
    let subtitle: String
    let disabled: Bool
    let value: OnboardingCore.State.Route
    
    var body: some View {
        Button {
            ViewStore(self.store).send(.updateRoute(value))
        } label: {
            HStack {
                VStack(alignment: .leading) {
                    Image(systemName: image)
                        .padding(.bottom, Padding.m)
                    Text(title)
                        .subtitle()
                    Text(subtitle)
                        .caption()
                }
                Spacer()
                Image(systemName: "arrow.forward")
                    .font(.title2)
            }
            .padding(Padding.l)
            .background(
                RoundedRectangle(cornerRadius: CornerRadius.m)
                    .fill(Color("SecondaryBackground"))
            )
            .opacity(disabled ? 0.7 : 1)
        }
        .disabled(disabled)
        .withScaleEffect()
    }
}

struct OnboardingView_Previews: PreviewProvider {
    static var previews: some View {
        OnboardingView(
            store: OnboardingStore(initialState: OnboardingCore.State(), reducer: OnboardingCore())
        )
    }
}
