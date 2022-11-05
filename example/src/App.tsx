import * as React from 'react';

import { StyleSheet, View, Text, ScrollView } from 'react-native';
import { testWNFSLib } from 'react-native-wnfs';

export default function App() {
  const [result, setResult] = React.useState<number | undefined>();

  React.useEffect(() => {
    testWNFSLib().then(setResult);
  }, []);

  return (
    <View style={styles.container}>
	<ScrollView>
      <Text>Result: {result}</Text>
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
