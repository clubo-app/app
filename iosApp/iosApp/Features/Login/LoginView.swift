//
//  LoginView.swift
//  iosApp
//
//  Created by Jonas Hiltl on 06.11.22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import KMMViewModelSwiftUI
import KMPNativeCoroutinesAsync
import shared

struct LoginView: View {
    @ObservedViewModel var viewModel: LoginViewModel
    
    var body: some View {
        VStack(alignment: .leading, spacing: Padding.xl) {
            Text("Welcome back, Sign up to see your Friends Parties and create your own.")
                .caption()
            
            Spacer()
            
            VStack(spacing: Padding.l) {
                TextField(
                    "Email",
                    text: Binding(get: { viewModel.email }, set: viewModel.changeEmail)
                )
                .filledTextField()
                .emailField()
                
                HStack {
                    Group {
                        if viewModel.showPassword {
                            TextField(
                                "Password",
                                text: Binding(
                                    get: { viewModel.password },
                                    set: viewModel.changePassword
                                )
                            )
                        } else {
                            SecureField(
                                "Password",
                                text: Binding(get: { viewModel.password }, set: viewModel.changePassword)
                            )
                        }
                    }
                    .textContentType(.password)
                    .autocorrectionDisabled()
                    
                    Button(
                        action: { viewModel.toggleShowPassword() },
                        label: {
                            Image(systemName: viewModel.showPassword ? "eye" : "eye.slash")
                        }
                    )
                    .foregroundColor(Color(UIColor.placeholderText))
                }
                .filledTextField()
            }
            
            
            Button(
                action: {
                    viewModel.login()
                },
                label: {
                    ZStack {
                        if viewModel.loginLoading {
                            ProgressView()
                                .colorInvert()
                        } else {
                            Text("Log in")
                        }
                    }
                }
            )
            .primaryButton()
            .disabled(viewModel.loginButtonDisabled)
            
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

struct LoginView_Previews: PreviewProvider {
    static var previews: some View {
        LoginView(
            viewModel: LoginViewModel()
        )
    }
}
