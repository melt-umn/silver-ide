## Getting Started

This Oberon0 example will show you step by step how to create a full IDE
experience for your extensible language in Atom. There are multiple
Markdown files each describing a step of the process. Do not be overwhelmed,
most of the information in `LanguageServerProtocol.md` can be useful but is
not strictly necessary to get Language Server Protocol support in your
language. Additionally, many design decisions were made which are specific to
Oberon0. You may feel free to use these design choices or make your own.

So, without further ado let me explain how to first set up your environment
to get everything working.

Download and Installs
1. Download [Atom version 1.38.2](https://github.com/atom/atom/releases).
This may work with new versions of Atom.
However, the version of Treesitter used in Atom must match the version
used on your computer to work properly. We are providing a set up that
works correctly. The documentation for which versions match up is limited,
but feel free to experiment to find a version pairing that fits your needs.
2. Download [Silver](https://github.com/melt-umn/silver)
using the installation guide [here](http://melt.cs.umn.edu/silver/install-guide/). After everything is installed,
check out the `feature/treesitter`.
3. Build the Silver LSP library jar using the script
`support/lsp/lsp-ide-build`.
4. Download [Silver-IDE](https://github.com/melt-umn/silver-ide/). Then run the `build-everything` script in
this directory to do all the necessary set up for the
repository.
5. Download Oberon0 check out the `lsp-support` branch
6. Downlaod Oberon0LSP and go to
 `SyntaxHighlightingInAtom.md` to continue



__Note__: The correct order to read the files is
 - `StartHere.md`
 - `SyntaxHighlightingInAtom.md`.
 The script `build-language-package` can be used to set up everything you need to do that is described in this file.
 - `AtomLSPSupport.md`  
 The script `build-lsp-package` can be used to set up
  everything you need to do that is described in this file
  and in the HookingIntoTheGenericLanguageServer.md`.
 - `HookingIntoTheGenericLanguageServer.md`

 - `LanguageServerProtocol.md`
