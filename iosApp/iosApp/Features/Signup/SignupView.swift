//
//  SignupV2.swift
//  iosApp
//
//  Created by Jonas Hiltl on 14.10.22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import ComposableArchitecture

struct SignupView: View {
    let store: StoreOf<SignupCore>
    
    struct ViewState: Equatable {
        let currentTab: SignupCore.State.Tab
        
        init(state: SignupCore.State) {
            currentTab = state.currentTab
        }
    }
    
    var body: some View {
        SignupViewV2(store: self.store)
        // WithViewStore(self.store, observe: ViewState.init) { viewStore in
//            TabView(
//                selection: viewStore.binding(
//                    get: \.currentTab,
//                    send: SignupCore.Action.tabSelected
//                )
//            ) {
//                ChooseMethod(
//                    store: self.store
//                )
//                .frame(maxWidth: .infinity, maxHeight: .infinity)
//                .gesture(DragGesture())      // this blocks swipe
//                .tag(SignupCore.State.Tab.method)
//
//                SetName(
//                    store: self.store
//                )
//                .frame(maxWidth: .infinity, maxHeight: .infinity)
//                .gesture(DragGesture())      // this blocks swipe
//                .tag(SignupCore.State.Tab.name)
//
//                SetPassword(
//                    store: self.store
//                )
//                .frame(maxWidth: .infinity, maxHeight: .infinity)
//                .gesture(DragGesture())      // this blocks swipe
//                .tag(SignupCore.State.Tab.password)
//            }
//            .padding(Padding.l)
//            .indexViewStyle(.page(backgroundDisplayMode: .always))
//            .toolbar(content: {
//                if viewStore.currentTab != SignupCore.State.Tab.method {
//                    ToolbarItem(placement: .navigationBarLeading) {
//                        Button(
//                            action: { viewStore.send(SignupCore.Action.tabBack) },
//                            label: {
//                                Image(systemName: "chevron.backward")
//                            }
//                        )
//                    }
//                }
//            })
//            .navigationBarBackButtonHidden(viewStore.currentTab != SignupCore.State.Tab.method)
//            .background(Color("Background")).ignoresSafeArea()
        //}
    }
}

struct SignupViewV2: View {
    let store: StoreOf<SignupCore>
    
    struct ViewState: Equatable {
        let firstname: String
        let lastname: String
        let username: String
        let email: String
        let password: String
        let usernameTaken: Bool
        let showPassword: Bool
        let showPasswordError: Bool
        let signUpLoading: Bool
        let buttonDisabled: Bool
        
        init (store: SignupCore.State) {
            firstname = store.firstname
            lastname = store.lastname
            username = store.username
            email = store.email
            password = store.password
            usernameTaken = store.usernameTaken
            showPassword = store.showPassword
            signUpLoading = store.signUpLoading
            showPasswordError = store.password != "" && !store.passwordValid
            buttonDisabled = store.firstname == "" || store.username == "" || store.usernameTaken || !store.emailValid || !store.passwordValid
        }
    }
    
    var body: some View {
        WithViewStore(self.store, observe: ViewState.init) { viewStore in
            VStack(spacing: Padding.l) {
                
                Text("Create your account")
                    .title()
               
                Spacer()
                
                HStack(spacing: Padding.l) {
                    TextField(
                        "First Name",
                        text: viewStore.binding(
                            get: \.firstname,
                            send: SignupCore.Action.firstnameChanged
                        ).removeDuplicates()
                    )
                    .filledTextField()
                    .textContentType(.givenName)
                    
                    TextField(
                        "Last Name",
                        text: viewStore.binding(
                            get: \.lastname,
                            send: SignupCore.Action.lastnameChanged
                        ).removeDuplicates()
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
                            text: viewStore.binding(
                                get: \.username,
                                send: SignupCore.Action.usernameChanged
                            ).removeDuplicates()
                        )
                        .textContentType(.username)
                        .textInputAutocapitalization(.never)
                        .autocorrectionDisabled()
                        
                        if viewStore.usernameTaken {
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
                    text: viewStore.binding(
                        get: \.email,
                        send: SignupCore.Action.emailChanged
                    ).removeDuplicates()
                )
                .filledTextField()
                .emailField()
                 
                VStack(alignment: .leading, spacing: Padding.s) {
                    
                    HStack {
                        Group {
                            if viewStore.showPassword {
                                TextField(
                                    "Password",
                                    text: viewStore.binding(
                                        get: \.password,
                                        send: SignupCore.Action.passwordChanged
                                    ).removeDuplicates()
                                )
                            } else {
                                SecureField(
                                    "Password",
                                    text: viewStore.binding(
                                        get: \.password,
                                        send: SignupCore.Action.passwordChanged
                                    ).removeDuplicates()
                                )
                            }
                        }
                        .textContentType(.password)
                        .autocorrectionDisabled()
                        
                        Button(
                            action: {
                                withAnimation {
                                    let _ = viewStore.send(.togglePassword)
                                }
                            },
                            label: {
                                Image(systemName: viewStore.showPassword ? "eye" : "eye.slash")
                            }
                        )
                        .foregroundColor(Color(UIColor.placeholderText))
                    }
                    .filledTextField()
                    
                    Text("Atleast 8 characters")
                        .foregroundColor(viewStore.showPasswordError ? Color("ErrorBorder") : .secondary)
                        .caption()
                }
                
                Spacer()
                 
                Button(
                    action: {
                        viewStore.send(.signupButtonPressed)
                    },
                    label: {
                        ZStack {
                            if viewStore.signUpLoading {
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
                .disabled(viewStore.buttonDisabled)
                
            }.padding(Padding.l)
        }
    }
}

struct ChooseMethod: View {
    let store: StoreOf<SignupCore>
    
    struct ViewState: Equatable {
        let email: String
        let emailValid: Bool
        let nextButtonDisabled: Bool

      init(state: SignupCore.State) {
          self.email = state.email
          self.emailValid = state.emailValid
          self.nextButtonDisabled = !state.emailValid
      }
    }

    var body: some View {
        WithViewStore(self.store, observe: ViewState.init) { viewStore in
            VStack(alignment: .leading, spacing: Padding.l) {
                Text("Choose your Method")
                Text("Sign up to see your Friends Parties and host your own.")
                    .caption()
                
                Spacer()
                
                TextField(
                    "Email",
                    text: viewStore.binding(
                        get: \.email,
                        send: SignupCore.Action.emailChanged
                    ).removeDuplicates()
                )
                .filledTextField()
                .emailField()
                
                Button("Continue") { viewStore.send(.tabSelected(.name))}
                    .primaryButton()
                    .disabled(viewStore.nextButtonDisabled)
               
                Spacer()
            }
        }
    }
}

struct SetName: View {
    let store: StoreOf<SignupCore>
    
    struct ViewState: Equatable {
        let firstname: String
        let lastname: String
        let username: String
        let usernameTaken: Bool
        let nextButtonDisabled: Bool
        
        init(state: SignupCore.State) {
            self.firstname = state.firstname
            self.lastname = state.lastname
            self.username = state.username
            self.usernameTaken = state.usernameTaken
            self.nextButtonDisabled = state.usernameTaken || state.username == "" || state.firstname == "" || state.lastname == ""
        }
    }

    var body: some View {
        WithViewStore(self.store, observe: ViewState.init) { viewStore in
            VStack(alignment: .leading, spacing: Padding.l) {
                Text("What's your Name?")
                Text("Your friends add you on Clubben via your username.")
                    .caption()
                
                Spacer()
                
                HStack(spacing: Padding.l) {
                    TextField(
                        "First Name",
                        text: viewStore.binding(
                            get: \.firstname,
                            send: SignupCore.Action.firstnameChanged
                        ).removeDuplicates()
                    )
                    .filledTextField()
                    .textContentType(.givenName)
                    .autocorrectionDisabled()
                    
                    TextField(
                        "Last Name",
                        text: viewStore.binding(
                            get: \.lastname,
                            send: SignupCore.Action.lastnameChanged
                        ).removeDuplicates()
                    )
                    .filledTextField()
                    .textContentType(.familyName)
                    .autocorrectionDisabled()
                }
                
                HStack {
                    Text("@")
                        .foregroundColor(Color(UIColor.placeholderText))
                    
                    TextField(
                        "Username",
                        text: viewStore.binding(
                            get: \.username,
                            send: SignupCore.Action.usernameChanged
                        ).removeDuplicates()
                    )
                    .textContentType(.username)
                    .textInputAutocapitalization(.never)
                    .autocorrectionDisabled()
                    
                    if viewStore.usernameTaken {
                          Image(systemName: "xmark")
                            .foregroundColor(.red)
                    } else {
                        Image(systemName: "checkmark")
                            .foregroundColor(.green)
                    }
                }
                .filledTextField()
                
                Button("Continue") { viewStore.send(.tabSelected(.password)) }
                    .primaryButton()
                    .disabled(viewStore.nextButtonDisabled)
                
                Spacer()
            }
        }
    }
}

struct SetPassword: View {
    let store: StoreOf<SignupCore>
    
    struct ViewState: Equatable {
        let password: String
        let showPassword: Bool
        let signUpLoading: Bool
        let buttonDisabled: Bool
        
        init(state: SignupCore.State) {
            self.password = state.password
            self.showPassword = state.showPassword
            self.signUpLoading = state.signUpLoading
            self.buttonDisabled = !state.passwordValid
        }
    }
    
    var body: some View {
        WithViewStore(self.store, observe: ViewState.init) { viewStore in
            VStack(alignment: .leading, spacing: Padding.l) {
                Text("Choose a Password")
                Text("Your password should be at least 8 characters long.")
                    .caption()
                
                Spacer()
                
                HStack {
                    Group {
                        if viewStore.showPassword {
                            TextField(
                                "Password",
                                text: viewStore.binding(
                                    get: \.password,
                                    send: SignupCore.Action.passwordChanged
                                ).removeDuplicates()
                            )
                        } else {
                            SecureField(
                                "Password",
                                text: viewStore.binding(
                                    get: \.password,
                                    send: SignupCore.Action.passwordChanged
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
                
                Button(
                    action: {
                        viewStore.send(.signupButtonPressed)
                    },
                    label: {
                        ZStack {
                            if viewStore.signUpLoading {
                                ProgressView()
                                    .colorInvert()
                            } else {
                                Text("Create your Account")
                            }
                        }
                    }
                )
                .primaryButton()
                .disabled(viewStore.buttonDisabled)
                
                Spacer()
            }
        }
    }
}

struct SignupView_Previews: PreviewProvider {
    static var previews: some View {
        SignupView(
            store: Store(initialState: SignupCore.State(), reducer: SignupCore())
        )
    }
}
