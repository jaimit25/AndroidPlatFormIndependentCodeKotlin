// ignore_for_file: deprecated_member_use
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: MyHomePage(),
    );
  }
}

class MyHomePage extends StatefulWidget {
  @override
  _MyHomePageState createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  String? value = "here you go";
  static const methodChannel =
      MethodChannel('com.example.androidcodespecific/method');
  int level = 0;

  _onPressed() async {
    final int? rtn = await methodChannel.invokeMethod('getValue');
    setState(() {
      value = rtn.toString();
    });
  }

  static Future<String> showBasicNotification({
    @required String smallIconName = "ic_launcher",
    @required String smallIconDefType = "mipmap",
  }) async {
    await methodChannel.invokeMethod('showBasicNotification', {
      "smallIconName": smallIconName,
      "smallIconDefType": smallIconDefType,
      "name" : " Jaimitkumar Panchal"
    });
    return "Done";
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        body: Center(
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          Text(value == "waiting" ? "Waiting..." : value.toString()),
          ElevatedButton(
            // onPressed: _onPressed,
            onPressed: () {
              // _onPressed();
              showBasicNotification(
                smallIconName: "ic_launcher",
                smallIconDefType: "mipmap",
              );
            },
            child: Text("Get Value"),
          )
        ],
      ),
    ));
  }
}
