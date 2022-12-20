//
//  Onboarding.swift
//  iosApp
//
//  Created by Jonas Hiltl on 05.10.22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

struct Onboarding: View {
    
    @EnvironmentObject var session: SessionManager
    @EnvironmentObject var notiQueue: NotificationManager

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
                        destination:  {
                            Signup(
                                viewmodel: SignupViewModel(session: session)
                            )
                        }
                    )
                    ChooseButton(
                        image: "wineglass",
                        title: "I'm an Organiser",
                        subtitle: "Coming soon...",
                        destination: {},
                        disabled: true
                    )
                }
                
                Spacer()
                
                HStack(alignment:.center) {
                    Text("Already have an Account?")
                    NavigationLink(
                        destination: Login(
                            viewmodel: LoginViewModel(session: session)
                        ),
                        label: { Text("Log in") }
                    )
                }
                    .frame(maxWidth: .infinity)
                
            }
                .padding(Padding.l)
                .background(Color("Background").ignoresSafeArea())
                .toolbar() {
                    ToolbarItem(placement: .automatic) {
                        Button("Skip", action: {
                            withAnimation {
                                session.skip()
                            }
                        })
                            .textButton()
                    }
                }
                .navigationTitle("Get Started")
        }
    }
}

struct Onboarding_Previews: PreviewProvider {
    static var previews: some View {
        Onboarding()
            .environmentObject(SessionManager(notiQueue: NotificationManager()))
    }
}

struct ChooseButton<Destination: View>: View {
    
    var image: String
    var title: String
    var subtitle: String
    @ViewBuilder let destination: () -> Destination
    var disabled: Bool = false
    
    var body: some View {
        NavigationLink(destination: destination, label: {
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
                .opacity(disabled ? 0.5 : 1)
        })
            .disabled(disabled)
            .withScaleEffect()
    }
}
