# CustomWebviewTabs (CWT)

![build](https://github.com/KaustubhPatange/CustomWebviewTabs/workflows/build/badge.svg)
![Maven Central](https://img.shields.io/maven-central/v/io.github.kaustubhpatange/cwt)

CWT is an alternative for CustomTabs in Android without needing a service provider.

Internally it uses `WebView` to load pages (also supports private browsing).

## Usage

- Check [sample](/sample) project to see complete implementation of the library.

```kotlin
CWT.Builder(context)
    .onPageLoadListener { ... }
    .onPageLoadingListener { ... }
    .onWindowClosedListener { ... }
    .apply {
        /** Optionally set other settings */

        // lookFeel.primaryColor = getColor(R.color.colorPrimary)
        // options.privateMode = true
    }
    .launch(urlString)
```

| Parameter               | Task                                                            |
| ----------------------- | --------------------------------------------------------------- |
| `lookFeel.primaryColor` | Changes view that uses primary color eg: `Toolbar`.             |
| `options.darkMode`      | Enable rendering pages in dark mode (API >= 29).                |
| `options.privateMode`   | Enable private browsing by not maintaining history and cookies. |

## Download

Library is available on `MavenCentral()`.

```kotlin
implementation "io.github.kaustubhpatange.cwt:<version>"
```

## License

- [The Apache License Version 2.0](https://www.apache.org/licenses/LICENSE-2.0.txt)

```
Copyright 2020 Kaustubh Patange

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
