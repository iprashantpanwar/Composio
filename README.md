# 🎨 Composio

**A collection of delightful animations built with Jetpack Compose.**

Composio is an open-source playground of smooth, creative, and reusable animations written entirely in **Jetpack Compose**.
It’s designed to help developers explore motion in Compose — from subtle micro-interactions to expressive UI transitions.

---

## ✨ Features

* 🔀 **Pure Compose animations** — built using `Canvas`, `rememberInfiniteTransition`, and `Animatable`.
* 💫 **Plug & play composables** — drop them directly into your UI.
* ⚙️ **Lightweight & modular** — each animation is self-contained and customizable.
* 🎜️ **Creative inspiration** — from morphing blobs to breathing loaders and gooey buttons.

---

## 🚀 Getting Started

### 1. Clone the repository

```bash
git clone https://github.com/iprashantpanwar/Composio.git
```

### 2. Open in Android Studio

Open the project in Android Studio **Flamingo or newer**, and ensure you’re using **Compose Compiler 1.5+** and **Kotlin 1.9+**.

### 3. Run the demo app

Hit **Run ▶️** on the sample module to preview all animations in action.

---

## 🧩 Example Usage

```kotlin
@Composable
fun AnimatedBlobDemo() {
    MorphingBlob(
        modifier = Modifier
            .size(260.dp)
            .padding(innerPadding),
        morphPoints = 6,
        blobStyle = BlobStyle(
            effect = BlobEffect(blurRadius = 1f, alpha = 0.5f),
            shader = BlobShader.Radial(
                colors = listOf(
                    Color(0xFFF97272), Color(
                        0xFF673AB7
                    )
                )
            ),
            shape = BlobShape.Fill
        )
    )
}
```

---

## 🌈 Showcase Animations

| Animation            | Preview                                                                                        | Description                                                               |
|----------------------|------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------|
| 💓 **Heartbeat**     | ![Heartbeat](https://github.com/iprashantpanwar/Composio/blob/main/arts/heartbeat.gif)         | A smooth pulsing animation with rhythmic heart beats.                     |
| 🌊 **RippleWave**    | ![WaveMotion](https://github.com/iprashantpanwar/Composio/blob/main/arts/ripplewavemotion.gif) | Dynamic, fluid wave animation using `Canvas`.                             |
| 🧠 **MorphingBlob**  | ![MorphingBlob](https://github.com/iprashantpanwar/Composio/blob/main/arts/blob.gif)           | Organic blob morphing through randomized control points and Bézier curves. |

## 🖼️ Contribution
> ️ *To contribute your animation preview:*
> Add a `.gif` in the `assets/` folder and link it here using a relative path.

---

## 🔧 Tech Stack

* **Language:** Kotlin
* **Framework:** Jetpack Compose
* **Build System:** Gradle (KTS)
* **Target:** Android API 24+

---

## 🤝 Contributing

Contributions are welcome!
If you have a cool animation idea or optimization, feel free to:

1. Fork the repo
2. Create a new branch (`feature/new-animation`)
3. Add your composable and GIF preview
4. Submit a pull request 🎉

---

Feel free to use it in your own projects — just give credit where it’s due. 💚

## 📸 Gallery

> *Here’s a glimpse of some of Composio’s living animations in action.*

---

## 📜 License

This project is licensed under the [MIT License](LICENSE).
