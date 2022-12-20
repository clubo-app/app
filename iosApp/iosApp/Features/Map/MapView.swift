//
//  MapView.swift
//  iosApp
//
//  Created by Jonas Hiltl on 24.11.22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import ComposableArchitecture

struct MapView: View {
    let store: MapStore
    
    var body: some View {
        Text("Map")
    }
}

struct MapView_Previews: PreviewProvider {
    static var previews: some View {
        MapView(
            store: MapStore(initialState: .init(), reducer: MapCore())
        )
    }
}
