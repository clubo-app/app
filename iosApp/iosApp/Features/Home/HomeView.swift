//
//  HomeView.swift
//  iosApp
//
//  Created by Jonas Hiltl on 16.11.22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import ComposableArchitecture

struct HomeView: View {
    let store: HomeStore
    
    var body: some View {
        WithViewStore(self.store) { viewStore in
            Text("Home")
        }
    }
}

struct HomeView_Previews: PreviewProvider {
    static var previews: some View {
        HomeView(
            store: Store(initialState: HomeCore.State(), reducer: HomeCore())
        )
    }
}
