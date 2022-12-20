//
//  ProfileView.swift
//  iosApp
//
//  Created by Jonas Hiltl on 24.11.22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import ComposableArchitecture

struct ProfileView: View {
    let store: ProfileStore
    
    var body: some View {
        WithViewStore(self.store) { viewStore in
            VStack {
                Text("Profile")
                Button("Sign Out") {
                    viewStore.send(.signOut)
                }
            }
        }
    }
}

struct ProfileView_Previews: PreviewProvider {
    static var previews: some View {
        ProfileView(
            store: ProfileStore(initialState: .init(), reducer: ProfileCore())
        )
    }
}
