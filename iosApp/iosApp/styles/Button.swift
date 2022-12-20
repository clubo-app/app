//
//  ButtonStyles.swift
//  iosApp
//
//  Created by Jonas Hiltl on 07.10.22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

struct ButtonStyles: View {
    var body: some View {
        VStack {
            Button{
                
            } label: {
                Text("Primary Button")
            }
                .primaryButton()
            
            Button{
                
            } label: {
                Text("Disabled Button")
            }
                .disabled(true)
                .primaryButton()
            
            Button{
                
            } label: {
                Text("Filled Button")
            }
                .filledButton()

            Button{
                
            } label: {
                Text("Text Button")
            }
                .textButton()
        }
    }
}

extension View {
    func primaryButton(stretch: Bool = true) -> some View {
        buttonStyle(PrimaryButtonStyle(stretch: stretch))
    }
}
struct PrimaryButtonStyle: ButtonStyle {
    @Environment(\.isEnabled) var isEnabled
    
    var stretch: Bool
        
    func makeBody(configuration: Configuration) -> some View {
        configuration.label
            .frame(maxWidth: .infinity)
            .foregroundColor(
                .white
                .opacity(isEnabled ? 1 : 0.5)
            )
            .padding(Padding.l)
            .background(RoundedRectangle(cornerRadius: CornerRadius.s)
                .fill(Color("AccentColor"))
                .opacity(isEnabled ? 1 : 0.4)
            )
            .scaleEffect(configuration.isPressed ? 0.95 : 1.0)
    }
}

extension View {
    func filledButton(stretch: Bool = true) -> some View {
        buttonStyle(FilledButtonStyle(stretch: stretch))
    }
}
struct FilledButtonStyle: ButtonStyle {
    @Environment(\.isEnabled) var isEnabled
    
    var stretch: Bool
        
    func makeBody(configuration: Configuration) -> some View {
        configuration.label
            .frame(maxWidth: .infinity)
            .foregroundColor(
                .primary
                .opacity(isEnabled ? 1 : 0.5)
            )
            .padding(Padding.l)
            .background(RoundedRectangle(cornerRadius: CornerRadius.s)
                .stroke(Color("TextFieldBorder"))
                .background(RoundedRectangle(cornerRadius: CornerRadius.s)
                    .fill(Color("SecondaryBackground"))
                )
                    .opacity(isEnabled ? 1 : 0.4)
            )
            .scaleEffect(configuration.isPressed ? 0.95 : 1.0)
    }
}


extension View {
    func textButton() -> some View {
        buttonStyle(TextButtonStyle())
    }
}
struct TextButtonStyle: ButtonStyle {
    @Environment(\.isEnabled) var isEnabled
        
    func makeBody(configuration: Configuration) -> some View {
        configuration.label
            .foregroundColor(Color("AccentColor"))
            .scaleEffect(configuration.isPressed ? 0.95 : 1.0)
    }
}

struct ScaleButtonStyle: ButtonStyle {
    
    let scaledAmount: CGFloat
    
    init(scaledAmount: CGFloat) {
        self.scaledAmount = scaledAmount
    }
    
    func makeBody(configuration: Configuration) -> some View {
        configuration.label
            .scaleEffect(configuration.isPressed ? scaledAmount : 1.0)
    }
}

extension View {
    func withScaleEffect(scaledAmount: CGFloat = 0.95) -> some View {
        buttonStyle(ScaleButtonStyle(scaledAmount: scaledAmount))
    }
}

struct ButtonStyles_Previews: PreviewProvider {
    static var previews: some View {
        ButtonStyles()
    }
}
