import Fula from '../interfaces/wnfsNativeModule';
//import type { Config } from '../interfaces';

/**
 * Get gets the value corresponding to the given key from the local datastore.
// The key must be a valid ipld.Link.
 * @param config
 * @returns boolean
 */
export const initJNI = (
  identity: string | null,
  storePath: string | null
): Promise<boolean> => {
  return Fula.initJNI(identity, storePath);
};

/**
 * Get gets the value corresponding to the given key from the local datastore.
// The key must be a valid ipld.Link.
 * @param key
 * @returns value
 */
export const getJNI = (key: string): Promise<string> => {
  return Fula.getJNI(key);
};
