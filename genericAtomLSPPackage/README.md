# IDE- package

Generic language support for Atom-IDE for languages written in Silver.
One file needs to be added for this generic package to work. This file is called
AtomPackageLSPMain.js. It can be generated by running Slide with --atom-lsp-package
flag.

Note you must install the atom-ide-ui package as well to utilize the functionality
of this server in Atom.

For reference: The enhancedScopes field in the package.json serves as
metadata for which languages (scopes) this package works for.
