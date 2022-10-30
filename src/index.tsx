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

export function testWNFSLib(): Promise<number> {
  return Wnfs.testWNFSLib();
}
