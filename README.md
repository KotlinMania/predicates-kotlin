# predicates-kotlin in Kotlin

[![GitHub link](https://img.shields.io/badge/GitHub-KotlinMania%2Fpredicates--kotlin-blue.svg)](https://github.com/KotlinMania/predicates-kotlin)
[![Maven Central](https://img.shields.io/maven-central/v/io.github.kotlinmania/predicates-kotlin)](https://central.sonatype.com/artifact/io.github.kotlinmania/predicates-kotlin)
[![Build status](https://img.shields.io/github/actions/workflow/status/KotlinMania/predicates-kotlin/ci.yml?branch=main)](https://github.com/KotlinMania/predicates-kotlin/actions)

This is a Kotlin Multiplatform line-by-line transliteration port of [`assert-rs/predicates-rs`](https://github.com/assert-rs/predicates-rs).

**Original Project:** This port is based on [`assert-rs/predicates-rs`](https://github.com/assert-rs/predicates-rs). All design credit and project intent belong to the upstream authors; this repository is a faithful port to Kotlin Multiplatform with no behavioural changes intended.

### Porting status

This is an **in-progress port**. The goal is feature parity with the upstream Rust crate while providing a native Kotlin Multiplatform API. Every Kotlin file carries a `// port-lint: source <path>` header naming its upstream Rust counterpart so the AST-distance tool can track provenance.

---

## Upstream README — `assert-rs/predicates-rs`

> The text below is reproduced and lightly edited from [`https://github.com/assert-rs/predicates-rs`](https://github.com/assert-rs/predicates-rs). It is the upstream project's own description and remains under the upstream authors' authorship; links have been rewritten to absolute upstream URLs so they continue to resolve from this repository.

## predicates-rs

> An implementation of **boolean-valued predicate functions** in Rust.

[![Documentation](https://img.shields.io/badge/docs-master-blue.svg)](https://docs.rs/predicates)
![License](https://img.shields.io/crates/l/predicates.svg)
[![Crates.io](https://img.shields.io/crates/v/predicates.svg?maxAge=2592000)](https://crates.io/crates/predicates)

[Changelog](https://github.com/assert-rs/predicates-rs/blob/master/CHANGELOG.md)


## Usage

First, add this to your `Cargo.toml`:

```toml
[dependencies]
predicates = "3.1.4"
```

Next, add this to your crate:

```rust
extern crate predicates;

use predicates::prelude::*;
```

For more information on using predicates, look at the
[documentation](https://docs.rs/predicates)

## License

Licensed under either of

* Apache License, Version 2.0, ([LICENSE-APACHE](https://github.com/assert-rs/predicates-rs/blob/HEAD/LICENSE-APACHE) or <https://www.apache.org/licenses/LICENSE-2.0>)
* MIT license ([LICENSE-MIT](https://github.com/assert-rs/predicates-rs/blob/HEAD/LICENSE-MIT) or <https://opensource.org/license/mit>)

at your option.

## Credits

Big thanks to [futures-rs](https://github.com/alexcrichton/futures-rs), whose
slick API design informed a lot of decisions made on the API design of this
library.

---

## About this Kotlin port

### Installation

```kotlin
dependencies {
    implementation("io.github.kotlinmania:predicates-kotlin:0.1.0")
}
```

### Building

```bash
./gradlew build
./gradlew test
```

### Targets

- macOS arm64
- Linux x64
- Windows mingw-x64
- iOS arm64 / simulator-arm64 (Swift export + XCFramework)
- JS (browser + Node.js)
- Wasm-JS (browser + Node.js)
- Android (API 24+)

### Porting guidelines

See [AGENTS.md](AGENTS.md) and [CLAUDE.md](CLAUDE.md) for translator discipline, port-lint header convention, and Rust → Kotlin idiom mapping.

### License

This Kotlin port is distributed under the same MIT license as the upstream [`assert-rs/predicates-rs`](https://github.com/assert-rs/predicates-rs). See [LICENSE](LICENSE) (and any sibling `LICENSE-*` / `NOTICE` files mirrored from upstream) for the full text.

Original work copyrighted by the predicates-rs authors.  
Kotlin port: Copyright (c) 2026 Sydney Renee and The Solace Project.

### Acknowledgments

Thanks to the [`assert-rs/predicates-rs`](https://github.com/assert-rs/predicates-rs) maintainers and contributors for the original Rust implementation. This port reproduces their work in Kotlin Multiplatform; bug reports about upstream design or behavior should go to the upstream repository.
