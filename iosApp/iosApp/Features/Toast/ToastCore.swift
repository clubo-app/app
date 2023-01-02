//
//  ToastCore.swift
//  iosApp
//
//  Created by Jonas Hiltl on 25.12.22.
//  Copyright © 2022 orgName. All rights reserved.
//

import shared
import SwiftUI

extension shared.Toast : Identifiable {
    var background: Color {
        switch variant {
        case shared.ToastVariant.error:
            return Color("ErrorBG")
        case shared.ToastVariant.warning:
            return Color("WarningGB")
        case shared.ToastVariant.success:
            return Color("SuccessBG")
        default:
            return Color("ErrorBG")
        }
    }
    
    var border: Color {
        switch variant {
        case shared.ToastVariant.error:
            return Color("ErrorBorder")
        case shared.ToastVariant.warning:
            return Color("WarningBorder")
        case shared.ToastVariant.success:
            return Color("SuccessBorder")
        default:
            return Color("ErrorBorder")
        }
    }
    
    var icon: String {
        switch variant {
        case shared.ToastVariant.error:
            return "xmark.circle.fill"
        case shared.ToastVariant.warning:
            return "exclamationmark.triangle.fill"
        case shared.ToastVariant.success:
            return "checkmark.circle.fill"
        default:
            return ""
        }
    }

}
