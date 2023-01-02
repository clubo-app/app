//
//  MapView.swift
//  iosApp
//
//  Created by Jonas Hiltl on 24.11.22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import MapKit

struct MapView: View {
    var body: some View {
       Map
        Text("Map")
    }
}

struct MapView_Previews: PreviewProvider {
    static var previews: some View {
        MapView()
    }
}
