//
//  FrameView.swift
//  iosApp
//
//  Created by Jonas Hiltl on 20.12.22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

struct FramView: View {
    var image: CGImage?
    
    private let label = Text("frame")
    
    var body: some View {
        if let image = image {
            Image(image, scale: 1.0, orientation: .up, label: label)
        } else {
            Color.cyan
        }
    }
}
