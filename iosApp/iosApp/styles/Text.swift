//
//  TextStyles.swift
//  iosApp
//
//  Created by Jonas Hiltl on 07.10.22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

extension View {
    public func title() -> some View {
        modifier(TitleStyle())
    }
}
struct TitleStyle: ViewModifier {
    func body(content: Content) -> some View {
        content
            .font(.title.weight(.semibold))
    }
}

extension View {
    public func subtitle() -> some View {
        modifier(SubtitleStyle())
    }
}
struct SubtitleStyle: ViewModifier {
    func body(content: Content) -> some View {
        content
            .font(.title3.weight(.semibold))
    }
}

extension View {
    public func caption() -> some View {
        modifier(CaptionStyle())
    }
}
struct CaptionStyle: ViewModifier {
    func body(content: Content) -> some View {
        content
            .font(.subheadline)
            .foregroundColor(.secondary)
            .multilineTextAlignment(.leading)
    }
}

struct TextStyles: View {
    var body: some View {
        VStack {
            Text("Title")
                .title()
            
            Text("Subtitle")
                .subtitle()
            
            Text("Body")
            
            Text("caption")
                .caption()
        }
    }
}

struct TextStyles_Previews: PreviewProvider {
    static var previews: some View {
        TextStyles()
    }
}
