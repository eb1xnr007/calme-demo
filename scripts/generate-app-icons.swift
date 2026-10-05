import CoreGraphics
import Foundation
import ImageIO
import UniformTypeIdentifiers

let root = URL(fileURLWithPath: FileManager.default.currentDirectoryPath)
let green = CGColor(red: 29 / 255, green: 138 / 255, blue: 99 / 255, alpha: 1)

func renderIcon(size: Int, round: Bool, path: String) throws {
  let context = CGContext(
    data: nil,
    width: size,
    height: size,
    bitsPerComponent: 8,
    bytesPerRow: 0,
    space: CGColorSpaceCreateDeviceRGB(),
    bitmapInfo: (round ? CGImageAlphaInfo.premultipliedLast : .noneSkipLast).rawValue
  )!
  context.clear(CGRect(x: 0, y: 0, width: size, height: size))
  context.scaleBy(x: CGFloat(size) / 108, y: CGFloat(size) / 108)
  context.translateBy(x: 0, y: 108)
  context.scaleBy(x: 1, y: -1)
  context.setFillColor(green)
  if round {
    context.fillEllipse(in: CGRect(x: 0, y: 0, width: 108, height: 108))
  } else {
    context.fill(CGRect(x: 0, y: 0, width: 108, height: 108))
  }

  // Same 24×24 paths as brand_logo.xml, enlarged within the launcher safe area.
  context.translateBy(x: 6, y: 6)
  context.scaleBy(x: 4, y: 4)
  context.setStrokeColor(CGColor(red: 1, green: 1, blue: 1, alpha: 1))
  context.setLineWidth(2)
  context.setLineCap(.round)
  context.setLineJoin(.round)

  let leaf = CGMutablePath()
  leaf.move(to: CGPoint(x: 12, y: 11))
  leaf.addCurve(
    to: CGPoint(x: 17.5, y: 5), control1: CGPoint(x: 12, y: 7.5), control2: CGPoint(x: 14.1, y: 5.3)
  )
  leaf.addCurve(
    to: CGPoint(x: 12, y: 11), control1: CGPoint(x: 17.2, y: 8.3),
    control2: CGPoint(x: 15.2, y: 10.4))
  leaf.closeSubpath()
  context.addPath(leaf)
  context.strokePath()

  let bowl = CGMutablePath()
  bowl.move(to: CGPoint(x: 12, y: 11))
  bowl.addCurve(
    to: CGPoint(x: 7.3, y: 8.5), control1: CGPoint(x: 10.5, y: 9.3), control2: CGPoint(x: 9, y: 8.6)
  )
  bowl.move(to: CGPoint(x: 5, y: 14))
  bowl.addLine(to: CGPoint(x: 19, y: 14))
  bowl.move(to: CGPoint(x: 6.5, y: 14))
  bowl.addCurve(
    to: CGPoint(x: 12, y: 18.8), control1: CGPoint(x: 7.3, y: 17.1),
    control2: CGPoint(x: 9.5, y: 18.8))
  bowl.addCurve(
    to: CGPoint(x: 17.5, y: 14), control1: CGPoint(x: 14.5, y: 18.8),
    control2: CGPoint(x: 16.7, y: 17.1))
  context.addPath(bowl)
  context.strokePath()
  let url = root.appendingPathComponent(path)
  let destination = CGImageDestinationCreateWithURL(
    url as CFURL, UTType.png.identifier as CFString, 1, nil)!
  CGImageDestinationAddImage(destination, context.makeImage()!, nil)
  guard CGImageDestinationFinalize(destination) else {
    throw NSError(domain: "CalMeIcons", code: 1)
  }
}

try renderIcon(
  size: 1024,
  round: false,
  path: "iosApp/src/Assets.xcassets/AppIcon.appiconset/app-icon-1024.png"
)
for (density, size) in [
  ("mdpi", 48), ("hdpi", 72), ("xhdpi", 96), ("xxhdpi", 144), ("xxxhdpi", 192),
] {
  let directory = "androidApp/res/mipmap-\(density)"
  try renderIcon(size: size, round: false, path: "\(directory)/ic_launcher.png")
  try renderIcon(size: size, round: true, path: "\(directory)/ic_launcher_round.png")
}
