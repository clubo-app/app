//
//  OnboardingView.swift
//  iosApp
//
//  Created by Jonas Hiltl on 07.11.22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import SwiftUINavigation
import KMMViewModelSwiftUI
import shared

struct OnboardingView: View {
    @ObservedViewModel var onboardingViewModel: OnboardingViewModel
    
    var body: some View {
        NavigationStack {
            VStack(alignment: .leading) {
                Text("Start by choosing your role.")
                    .caption()
                
                Spacer()
                
                VStack(spacing: Padding.l) {
                    ChooseButton(
                        image: "party.popper",
                        title: "I'm a Partyer",
                        subtitle: "Find the hottest parties wherever you are or create your own Parties.",
                        disabled: false,
                        onPress: onboardingViewModel.goToSignup
                    )
                    ChooseButton(
                        image: "wineglass",
                        title: "I'm an Organiser",
                        subtitle: "Coming soon...",
                        disabled: true,
                        onPress: {}
                    )
                }
                
                Spacer()
                
                HStack(alignment:.center) {
                    Text("Already have an Account?")
                    Button {
                        onboardingViewModel.goToLogin()
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
                        onboardingViewModel.skipAuth()
                    })
                    .textButton()
                }
            }
            .navigationTitle("Get Started")
            .navigationDestination(
                unwrapping: $onboardingViewModel.destination,
                case: /OnboardingViewModel.Destination.login
            ) { $loginViewModel in
                LoginView(
                    viewModel: loginViewModel
                )
            }
            .navigationDestination(
                unwrapping: $onboardingViewModel.destination,
                case: /OnboardingViewModel.Destination.signup
            ) { $signupViewModel in
                SignupView(
                    viewModel: signupViewModel
                )
            }
        }
    }
}

struct ChooseButton: View {
    let image: String
    let title: String
    let subtitle: String
    let disabled: Bool
    let onPress: () -> Void
    
    var body: some View {
        Button {
            onPress()
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
            onboardingViewModel: .init()
        )
    }
}
