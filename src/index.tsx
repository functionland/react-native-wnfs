import { NativeModules, Platform } from 'react-native';

const LINKING_ERROR =
  `The package 'react-native-wnfs' doesn't seem to be linked. Make sure: \n\n` +
  Platform.select({ ios: "- You have run 'pod install'\n", default: '' }) +
  '- You rebuilt the app after installing the package\n' +
  '- You are not using Expo Go\n';

const Wnfs = NativeModules.Wnfs
  ? NativeModules.Wnfs
  : new Proxy(
      {},
      {
        get() {
          throw new Error(LINKING_ERROR);
        },
      }
    );

export function createPrivateForest(dbPath: string): Promise<string> {
  return Wnfs.createPrivateForest(dbPath);
}

export function createRootDir(dbPath: string, cid: string): Promise<any> {
  return Wnfs.createRootDir(dbPath, cid);
}

export function writeFile(
  dbPath: string,
  cid: string,
  privateRef: String,
  filePath: String,
  localFilePath: String
): Promise<string> {
  return Wnfs.writeFile(dbPath, cid, privateRef, filePath, localFilePath);
}

export function readFile(
  dbPath: string,
  cid: string,
  privateRef: String,
  filePath: String
): Promise<Uint8Array> {
  return Wnfs.readFile(dbPath, cid, privateRef, filePath);
}

export function ls(
  dbPath: string,
  cid: string,
  privateRef: String,
  filePath: String
): Promise<any> {
  return Wnfs.ls(dbPath, cid, privateRef, filePath);
}
