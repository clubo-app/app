//
//  LoginView.swift
//  iosApp
//
//  Created by Jonas Hiltl on 06.11.22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import ComposableArchitecture

struct LoginView: View {
    let store: StoreOf<LoginCore>
     
    struct ViewState: Equatable {
        let email: String
        let password: String
        let showPassword: Bool
        let loginDisabled: Bool
        let loginLoading: Bool
        
        init(state: LoginCore.State) {
            self.email = state.email
            self.password = state.password
            self.showPassword = state.showPassword
            self.loginDisabled = !state.emailValid || !state.passwordValid
            self.loginLoading = state.loginLoading
        }
    }
    
    var body: some View {
        WithViewStore(self.store, observe: ViewState.init) { viewStore in
            VStack(alignment: .leading, spacing: Padding.xl) {
                Text("Welcome back, Sign up to see your Friends Parties and create your own.")
                    .caption()
                
                Spacer()
                
                VStack(spacing: Padding.l) {
                    TextField(
                        "Email",
                        text: viewStore.binding(
                            get: \.email,
                            send: LoginCore.Action.emailChanged
                        ).removeDuplicates()
                    )
                    .filledTextField()
                    .emailField()
                    
                    HStack {
                        Group {
                            if viewStore.showPassword {
                                TextField(
                                    "Password",
                                    text: viewStore.binding(
                                        get: \.password,
                                        send: LoginCore.Action.passwordChanged
                                    ).removeDuplicates()
                                )
                            } else {
                                SecureField(
                                    "Password",
                                    text: viewStore.binding(
                                        get: \.password,
                                        send: LoginCore.Action.passwordChanged
                                    ).removeDuplicates()
                                )
                            }
                        }
                        .textContentType(.password)
                        .autocorrectionDisabled()

                        Button(
                            action: { viewStore.send(.togglePassword) },
                            label: {
                                Image(systemName: viewStore.showPassword ? "eye" : "eye.slash")
                            }
                        )
                        .foregroundColor(Color(UIColor.placeholderText))
                    }
                    .filledTextField()
                }
                
               
                Button(
                    action: {
                        viewStore.send(.loginButtonPressed)
                    },
                    label: {
                        ZStack {
                            if viewStore.loginLoading{
                                ProgressView()
                                    .colorInvert()
                            } else {
                                Text("Log in")
                            }
                        }
                    }
                )
                .primaryButton()
                .disabled(viewStore.loginDisabled)
                 
                HStack {
                    VStack {
                        Divider()
                    }
                    Text("or")
                        .caption()
                    VStack {
                        Divider()
                    }
                }
                 
                HStack {
                    Button(action: {}, label: {
                        Image("Google")
                            .resizable()
                            .frame(width: 20, height: 20)
                    })
                    .filledButton()
                    
                    Button(action: {}, label: {
                        Image(systemName: "apple.logo")
                            .resizable()
                            .scaledToFit()
                            .frame(width: 20, height: 20)
                    })
                    .filledButton()
                }
            
                Spacer()
             
                HStack(alignment:.center) {
                    Text("Forgotton your Password?")
                    NavigationLink(destination: {}, label: {Text("Reset")})
                }
                    .frame(maxWidth: .infinity)
            
            }
            .padding(Padding.l)
            .background(Color("Background").ignoresSafeArea())
            .navigationTitle("Log in")

        }
    }
}

struct LoginView_Previews: PreviewProvider {
    static var previews: some View {
        LoginView(
            store: Store(initialState: LoginCore.State(), reducer: LoginCore())
        )
    }
}
