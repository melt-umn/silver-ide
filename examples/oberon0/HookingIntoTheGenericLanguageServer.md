Hooking Into The Generic Language Server
----------------------------------------

Silver is not the best language for working with things such
as interprocess communication. Thus, we have a
language agnostic server written in Java that handles this
part and calls
the LSP library functions that you have hooked into.

However, for the language agnostic server to know about your
hooks you need to make sure the jar produced by your LSP is
a dependency of the language agnostic server and you need to
manually call the Silver init functions.

This is admittedly pretty ugly right now, but this only needs
to be done once and the changes you make are pretty cut and
paste where a script could be used to automate this. So,
that is exactly what we did. The script
`hook-into-genericLanguageServer` should be run to produce
a language server for your language. Simply run the script in
the directory where your jar is located and provide the jar
name as an argument to the script. This should do everything
else that is necessary to produce a usable language server
for the implementation you provided in Silver.

To get things to work in Atom look at the
[AtomLSPSupport.md](./AtomLSPSupport.md) folder for help.
