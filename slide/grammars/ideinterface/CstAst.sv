grammar ideinterface;

imports silver:extension:ideinterface;
imports concretesyntax:regex only regexString;
imports abstractsyntax;
imports driver;


synthesized attribute atomLanguageFile :: String occurs on IDEInterfaceSyntaxRoot;
synthesized attribute atomLSPFile :: String occurs on IDEInterfaceSyntaxRoot;
autocopy attribute spec :: Spec occurs on IDEInterfaceSyntaxRoot, IDEInterfaceSyntax, IDEInterfaceSyntaxDcl;
autocopy attribute buildEnv :: BuildEnv occurs on IDEInterfaceSyntaxRoot, IDEInterfaceSyntax, IDEInterfaceSyntaxDcl;

aspect production ideSyntaxRoot
top::IDEInterfaceSyntaxRoot ::= s::IDEInterfaceSyntax
{
  local languageName :: String = top.spec.langName.fromJust;
  local lspDirName :: String = substitute(".", "-", top.spec.lspJarName.fromJust);

  local firstLineRegex :: String = 
    if top.spec.firstLineRegex.isJust then
      s"""firstLineRegex: ['${top.spec.firstLineRegex.fromJust.regexString}']"""
    else
      "";

  top.atomLanguageFile = s"""
  name: '${languageName}'
  scopeName: '${languageName}'
  type: 'tree-sitter'
  parser: '${top.spec.treesitterParserName.fromJust}'

  fileTypes: ['${implode(",", top.spec.fileExtensions)}']
  ${firstLineRegex}

  scopes:
    ${s.atomScopes}

  """;

  top.atomLSPFile = s"""
  const {AutoLanguageClient} = require('atom-languageclient')
  const cp = require('child_process')
  const debug = true


  class ${languageName}LanguageClient extends AutoLanguageClient {
    getGrammarScopes () { return [ '${languageName}' ] }
    getLanguageName () { return '${languageName}' }
    getServerName () { return '${languageName}-LanguageServer' }

    startServerProcess () {
      const command = "java"
      const args = ["-jar", "${top.buildEnv.silverIDEHome}/generated/LSPServers/${lspDirName}LanguageServer/target/${lspDirName}-1.0.jar"]
      const childProcess = cp.spawn(command, args)
      this.captureServerErrors(childProcess)
      if (debug) {
        console.log("Started child process")
      }
      childProcess.on('close', exitCode => {
        if (!childProcess.killed) {
        atom.notifications.addError('${languageName}-LanguageServer stopped unexpectedly.', {
          dismissable: true,
          description: this.processStdErr ? "<code> + "this.processStdErr + "</code>" : "Exit code: " + exitCode
        })
      }
    })
    return childProcess
  }

    //shouldStartForEditor() {
    //  // figure out good way to start this (proejct file?)
    //  use default for now which is if any file is open in the editor
    //}
  }

  module.exports = new ${languageName}LanguageClient()
  """;
}
