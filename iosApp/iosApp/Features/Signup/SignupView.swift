//
//  SignupV2.swift
//  iosApp
//
//  Created by Jonas Hiltl on 14.10.22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import KMPNativeCoroutinesAsync
import KMMViewModelSwiftUI
import shared

struct SignupView: View {
    @ObservedViewModel var viewModel: SignUpViewModel
    
    var body: some View {
        VStack(spacing: Padding.l) {
            Text("Create your account")
                .title()
            
            Spacer()
            
            HStack(spacing: Padding.l) {
                TextField(
                    "First Name",
                    text: Binding(
                        get: { viewModel.firstname },
                        set: viewModel.changeFirstname
                    )
                )
                .filledTextField()
                .textContentType(.givenName)
                
                TextField(
                    "Last Name",
                    text: Binding(
                        get: { viewModel.lastname },
                        set: viewModel.changeLastname
                    )
                )
                .filledTextField()
                .textContentType(.familyName)
            }
            
            VStack(alignment: .leading, spacing: Padding.s) {
                HStack {
                    Text("@")
                        .foregroundColor(Color(UIColor.placeholderText))
                    
                    TextField(
                        "Username",
                        text: Binding(
                            get: { viewModel.username },
                            set:  viewModel.changeUsername
                        )
                    )
                    .textContentType(.username)
                    .textInputAutocapitalization(.never)
                    .autocorrectionDisabled()
                    
                    if viewModel.usernameTaken {
                        Image(systemName: "xmark")
                            .foregroundColor(.red)
                    } else {
                        Image(systemName: "checkmark")
                            .foregroundColor(.green)
                    }
                }
                .filledTextField()
                
                Text("Your friends add you via your username.")
                    .caption()
                
            }
            
            TextField(
                "Email",
                text: Binding(
                    get: { viewModel.email },
                    set: viewModel.changeEmail
                )
            )
            .filledTextField()
            .emailField()
            
            VStack(alignment: .leading, spacing: Padding.s) {
                
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
                                text: Binding(
                                    get: { viewModel.password },
                                    set: viewModel.changePassword
                                )
                            )
                        }
                    }
                    .textContentType(.password)
                    .autocorrectionDisabled()
                    
                    Button(
                        action: {
                            withAnimation {
                                viewModel.toggleShowPassword()
                            }
                        },
                        label: {
                            Image(systemName: viewModel.showPassword ? "eye" : "eye.slash")
                        }
                    )
                    .foregroundColor(Color(UIColor.placeholderText))
                }
                .filledTextField()
                
                Text("Atleast 8 characters")
                    .foregroundColor(
                        viewModel.showPasswordError
                            ? Color("ErrorBorder")
                            : .secondary
                    )
                    .caption()
            }
            
            Spacer()
            
            Button(
                action: {
                    viewModel.signUp()
                },
                label: {
                    ZStack {
                        if viewModel.signUpLoading {
                            ProgressView()
                                .colorInvert()
                        } else {
                            Text("Sign Up")
                        }
                    }
                }
            )
            .primaryButton()
            .padding(.vertical, Padding.l)
            .disabled(viewModel.signUpButtonDisabled)
        }.padding(Padding.l)
    }
}
