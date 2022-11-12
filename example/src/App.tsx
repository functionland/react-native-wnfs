import * as React from 'react';
import { StyleSheet, View, Text, ScrollView } from 'react-native';
import {
  createPrivateForest,
  createRootDir,
  writeFile,
  readFile,
  ls,
} from 'react-native-wnfs';
import { Dirs, FileSystem } from 'react-native-file-access';

export default function App() {
  const [cid, setCid] = React.useState<string | undefined>();
  const [config, setConfig] = React.useState<string | undefined>();
  const [files, setFiles] = React.useState<string | undefined>();
  const [content, setContent] = React.useState<string | undefined>();

  React.useEffect(() => {
    async function testOverall() {
      let dbPath = Dirs.CacheDir + '/tmp';
      createPrivateForest(dbPath).then(async (mCid) => {
        setCid(mCid);
        let mConfigStr = await createRootDir(dbPath, mCid);
        let mConfig = JSON.parse(mConfigStr);
        setConfig(mConfigStr);
        let testFilePath = Dirs.CacheDir + '/test.txt';
        let privateRef = JSON.stringify(mConfig.private_ref);
        mCid = mConfig.cid;
        console.log(privateRef);
        FileSystem.writeFile(testFilePath, 'Hello, World!', 'utf8');

        mCid = await writeFile(
          dbPath,
          mCid,
          privateRef,
          'root/test.txt',
          testFilePath
        );

        let fileList = await ls(dbPath, mCid, privateRef, 'root');
        console.log(fileList);
        setFiles(fileList.split(','));
        let fileData = await readFile(
          dbPath,
          mCid,
          privateRef,
          'root/test.txt'
        );
        setContent(fileData.toString());
      });
    }
    testOverall();
  }, []);

  return (
    <View style={styles.container}>
      <ScrollView>
        <Text>Cid: {cid}</Text>
        <Text>Config: {config}</Text>
        <Text>files (ls result): {files}</Text>
        <Text>Text file ("root/test.txt"): {content}</Text>
      </ScrollView>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
  box: {
    width: 60,
    height: 60,
    marginVertical: 20,
  },
});
