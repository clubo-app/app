//
//  CameraView.swift
//  iosApp
//
//  Created by Jonas Hiltl on 20.12.22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

struct CameraView: View {
    @StateObject private var model = FrameHandler()
    
    var body: some View {
        FramView(image: model.frame)
            .ignoresSafeArea()
    }
}

struct CameraView_Previews: PreviewProvider {
    static var previews: some View {
        CameraView()
    }
}
