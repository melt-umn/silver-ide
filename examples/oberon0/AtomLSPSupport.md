Atom Language Server Protocol Support
-------------------------------------
To use Language Server Protocol servers and features
written in Silver in Atom there are 5 things you need to

1. Install the `atom-ide-ui` package in Atom to expose the
functionality of language servers in the IDE
2. Build your language server in Silver
3. Add the jar name (without the `.jar` part) and the
language name to the global language properties in a Slide
file. So for oberon0 this would look like
```
specification Oberon0LSP;
global ide_specification {
      language_name = oberon0;
      lsp_jar_name = oberon0.lsp;
}
```
then in another file we need to list which specifications
we are using so simply create a file called `SpecificationList.txt` (it can be named anything) and put in it
```
Oberon0LSP;
```
This will be expanded as we get into highlighting in Atom.

4. Run Slide with the `--atom-lsp-file` to generate a file
called `AtomPackageLSPMain.js`
5. Run the script `gen-atom-lsp-package` from the directory
containing `AtomPackageLSPMain.js` with the same language
name you listed above (in our case oberon0).

Thus, your bash should execute the following commands
```
apm install atom-ide-ui
silver -I grammars -I ../Oberon0 --one-jar oberon0:lsp
silver -I grammars/ -I ../Oberon0 --ide-interface edu:umn:cs:melt:Oberon0:components:L4
slide -I slide --atom-lsp-file ideInterface.txt slide/SpecificationList.txt
gen-atom-lsp-package oberon0
```
The second run of Silver is necessary to get the IDE
interface for Oberon0. This is necessary for highlighting
but not for LSP but Slide is not smart enough yet to be
able to know when it does or does not need the IDE interface.
Thus, we get it for now.
