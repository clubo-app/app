//
//  Constants.swift
//  iosApp
//
//  Created by Jonas Hiltl on 09.10.22.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import SwiftUI

struct Padding {
    static let s: CGFloat = 4.0
    static let m: CGFloat = 8.0
    static let l: CGFloat = 12.0
    static let xl: CGFloat = 18.0
}

struct CornerRadius {
    static let s: CGFloat = 8.0
    static let m: CGFloat = 12.0
    static let l: CGFloat = 18.0
    static let xl: CGFloat = 24.0
}


struct Constants: View {
    var body: some View {
        VStack {
            Button{} label: {
                Text("Primary Button")
            }
            .primaryButton()
            
            TextField("Placeholder", text: .constant("Error State"))
                .filledTextField()
        }
    }
}

struct Constants_Previews: PreviewProvider {
    static var previews: some View {
        Constants()
    }
}
