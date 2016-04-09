package com.cyl.myclasscontacts;

import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cyl.myclasscontacts.adapter.MySortAdapter;
import com.cyl.myclasscontacts.db.DBoperation;
import com.cyl.myclasscontacts.db.TelInfo;
import com.cyl.myclasscontacts.manager.Global;
import com.cyl.myclasscontacts.manager.UpdateManager;
import com.cyl.myclasscontacts.utils.ChineseToPinyinHelper;
import com.cyl.myclasscontacts.utils.CourseInfo;
import com.cyl.myclasscontacts.utils.HttpService;
import com.cyl.myclasscontacts.utils.JsonParsing;
import com.cyl.myclasscontacts.utils.SidebarView;
import com.cyl.myclasscontacts.utils.UserModel;
import com.cyl.myclasscontacts.utils.Utils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.io.File;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    @ViewInject(R.id.listView_users)
    private ListView listView_users;
    @ViewInject(R.id.textView_emptyinfo)
    private TextView textView_emptyinfo;

    private List<UserModel> totallList = null;

    @ViewInject(R.id.sidebarView_main)
    private SidebarView sidebarView_main;
    @ViewInject(R.id.textView_dialog)
    private TextView textView_dialog;

    private static MySortAdapter adapter = null;

    private String flag = null;
    private DBoperation DB;

    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    new SweetAlertDialog(MainActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("同步成功!")
                            .setContentText("已同步云端数据")
                            .show();
                    initview();
                    break;
                case 2:
                    new SweetAlertDialog(MainActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("ERROR")
                            .setContentText("网络连接超时!")
                            .show();
                    break;
                case 3:
                    new SweetAlertDialog(MainActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("删除成功!")
                            .setContentText("已同步云端数据")
                            .show();
                    initview();
                    break;
                case 4:
                    new SweetAlertDialog(MainActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("添加成功!")
                            .setContentText("已同步云端数据")
                            .show();
                    initview();
                    break;
                case 5:
                    new SweetAlertDialog(MainActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("修改成功!")
                            .setContentText("已同步云端数据")
                            .show();
                    initview();
                    break;

            }
        }
    };
    private SweetAlertDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DB = new DBoperation(this);

        listView_users = (ListView) findViewById(R.id.listView_users);
        textView_emptyinfo = (TextView) findViewById(R.id.textView_emptyinfo);
        sidebarView_main = (SidebarView) findViewById(R.id.sidebarView_main);
        textView_dialog = (TextView) findViewById(R.id.textView_dialog);

        //获取数据
        Intent intent = getIntent();
        flag = intent.getStringExtra("flag");
        if ("add".equals(flag)) {
//            updateDate(flag);
        }else if ("update".equals(flag)){
//            updateDate(flag);
        }
        initview();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                TextView Id = (TextView) findViewById(R.id.tv_user_id);
                TextView Name = (TextView) findViewById(R.id.tv_user_name);
                Map<String, String> map = Utils.getUserInfo(getApplicationContext());
                Id.setText(map.get("userID"));
                Name.setText(map.get("userName"));
                LinearLayout nav_header = (LinearLayout) findViewById(R.id.nav_heard);
                nav_header.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent =new Intent(MainActivity.this,UserInfoActivity.class);
                        startActivity(intent);
                    }
                });

            }
        };

        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    private void initview() {
        ViewUtils.inject(this);
        sidebarView_main.setTextView_dialog(textView_dialog);
        totallList = new ArrayList<UserModel>();
        totallList = getUserList();

        Collections.sort(totallList, new Comparator<UserModel>() {
            @Override
            public int compare(UserModel lhs, UserModel rhs) {
                if (lhs.getFirstLetter().equals("#")) {
                    return 1;
                } else if (rhs.getFirstLetter().equals("#")) {
                    return -1;
                } else {
                    return lhs.getFirstLetter().compareTo(rhs.getFirstLetter());
                }
            }
        });
        adapter = new MySortAdapter(getApplicationContext(), totallList);

        listView_users.setAdapter(adapter);
        listView_users.setEmptyView(textView_emptyinfo);
        Log.e("点击事件", "1223");
        listView_users.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Log.e("点击事件132", "23");
                String username = totallList.get(position).getUsername();
                String userNumber = totallList.get(position).getUserNumber();
                String userPhone = totallList.get(position).getUserPhone();
                initPhoneView(username, userNumber, userPhone,id);

            }
        });

        sidebarView_main
                .setOnLetterClickedListener(new SidebarView.OnLetterClickedListener() {
                    @Override
                    public void onLetterClicked(String str) {
                        int position = adapter.getPositionForSection(str
                                .charAt(0));
                        Log.e("点击事件132", String.valueOf(position));
                        listView_users.setSelection(position);
                    }
                });
    }

    /**
     * 初始化数据，将数据保存到sqlite
     */
    private void initData() {

        new Thread() {
            @Override
            public void run() {
                String result=null;
                List<TelInfo> listinfo = new ArrayList<TelInfo>();
                try {
                    //获取key
                    String key = HttpService.DataByGetKey();
                    final Map<String, String> useInfos = Utils.getUserInfo(getApplicationContext());
                    Log.e("", "" + useInfos.get("userClass"));
                    String[] datas = {"key=" + key, "phonebookOperate=query", "class=" + useInfos.get("userClass")};
                    result = HttpService.DataByPost(datas);
                } catch (Exception e) {
                    Log.e("网络连接失败","");
                    e.printStackTrace();
                }
                Log.e("initData:result",result+"值");
                listinfo =JsonParsing.phonenumber(result);
                DB.alter();
                for (int i = 0; i < listinfo.size(); i++) {
                    TelInfo t = new TelInfo(listinfo.get(i).getName(), listinfo.get(i).getNumber(), listinfo.get(i).getTel());
                    DB.insert(t);
                    Log.e("插入数据","成功");
                }
            }

        }.start();


    }
    private void initPhoneView(String username, final String userNumber, final String userPhone, final long id) {
        final Map<String, String> userinfo = Utils.getUserInfo(getApplicationContext());
        Log.e("学号 ", "" + userNumber + "====" + userinfo.get("userID"));
        if (!userNumber.equals(userinfo.get("userID"))) {
            AlertDialog.Builder phonedialog = new AlertDialog.Builder(MainActivity.this);
            phonedialog.setTitle(username + "的手机号\n" + userPhone);
            phonedialog.setMessage("拨打电话");
            phonedialog.setIcon(R.drawable.icon_luanch);
            phonedialog.setCancelable(false);
            phonedialog.setNegativeButton("拨打",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //跳转到手机自带的拨号界面
                            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + userPhone));
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);

                        }
                    });
            phonedialog.setPositiveButton("取消",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Log.e("点击了", "取消");
                        }
                    });
            phonedialog.show();
        } else {
            AlertDialog.Builder phonedialog = new AlertDialog.Builder(MainActivity.this);
            phonedialog.setTitle("我的手机号" + "\n" + userPhone);
            phonedialog.setMessage("修改 or 删除");
            phonedialog.setIcon(R.drawable.icon_luanch);
            phonedialog.setCancelable(false);
            phonedialog.setNegativeButton("删除号码",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //跳转到手机自带的拨号界面
                            new SweetAlertDialog(MainActivity.this, SweetAlertDialog.WARNING_TYPE)
                                    .setTitleText("Are you sure?")
                                    .setContentText("Won't be able to recover this phone!")
                                    .setConfirmText("Yes,delete it!")
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sDialog) {
                                            // reuse previous dialog instance
                                            new Thread() {
                                                @Override
                                                public void run() {

                                                    //获取key
                                                    String key = HttpService.DataByGetKey();
                                                    Log.e("key返回值", key);
                                                    String[] datas = {"key=" + key, "phonebookOperate=delete", "num=" + userinfo.get("userID"),
                                                            "name=" + userinfo.get("userName"), "class=" + userinfo.get("userClass"), "phone=" + userPhone
                                                    };

                                                    final String result = HttpService.DataByPost(datas);

                                                    Log.e("点击事件", "点击确定删除" + result);
                                                    Log.e("返回值", result);
                                                    String status = "{\"status\":\"success\"}";
                                                    String status1 = "{\"status\":\"record has existed\"}";
                                                    Log.e("标准值", status);

//                        Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
                                                    if (status.equals(result)) {
                                                        runOnUiThread(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                DB.delete(id);
                                                                initview();
                                                                adapter.notifyDataSetChanged();
//                                                                updateDate("delete");
//
                                                            }
                                                        });
                                                    } else if (status1.equals(result)) {
                                                        //请求失败
                                                        runOnUiThread(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                Toast.makeText(MainActivity.this, "号码不存在！", Toast.LENGTH_SHORT).show();
                                                            }
                                                        });
                                                    } else {
                                                        //请求失败
                                                        runOnUiThread(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();
                                                            }
                                                        });
                                                    }
                                                }
                                            }.start();
                                            sDialog.setTitleText("Deleted!")
                                                    .setContentText("Your phone has been deleted!")
                                                    .setConfirmText("OK")
                                                    .setConfirmClickListener(null)
                                                    .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                                        }
                                    })
                                    .show();
                        }
                    });
            phonedialog.setPositiveButton("取消",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Log.e("点击了", "取消");
                        }
                    });
            phonedialog.setNeutralButton("修改号码",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(getApplicationContext(), UpdatePhoneActivity.class);
                            intent.putExtra("phone", userPhone);
                            startActivity(intent);
                        }
                    });
            phonedialog.show();
        }
    }

    private List<UserModel> getUserList() {
        List<UserModel> list = new ArrayList<UserModel>();
        List<TelInfo> info = DB.queryAll();

        if (info != null) {
            for (int i = 0; i < info.size(); i++) {
                Log.e("数据库中有的联系人", info.get(i).getName()+" getUserList");

            }
            //  String[] img_src_data = getResources().getStringArray(R.array.img_src_data);
            for (int i = 0; i < info.size(); i++) {
                if(info.get(i).getName()!=null) {
                    UserModel userModel = new UserModel();
                    String username = info.get(i).getName();
                    String userPhone = info.get(i).getTel();
                    String userNumber = info.get(i).getNumber();
                    Log.e("姓名", username + "空");
                    // String image = img_src_data[i];
                    String pinyin = ChineseToPinyinHelper.getInstance().getPinyin(
                            username);
                    String firstLetter = pinyin.substring(0, 1).toUpperCase();
                    if (firstLetter.matches("[A-Z]")) {
                        userModel.setFirstLetter(firstLetter);
                    } else {
                        userModel.setFirstLetter("#");
                    }
                    userModel.setUsername(username);
                    userModel.setUserNumber(userNumber);
                    userModel.setUserPhone(userPhone);
                    list.add(userModel);
                }
            }
        } else {
            Log.e("list", "为空");
            return list;

        }
        return list;
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    private ProgressDialog pd;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent =new Intent(MainActivity.this,SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.action_update) {
            updateDate();
            adapter.notifyDataSetChanged();
            return true;
        }
        if (id == R.id.action_import) {
            //AsyncTask<执行异步任务时所必须的参数（如果必须的话），异步任务执行的进度，异步任务执行完毕后的返回值（就是backgroud方法的返回值类型）>——————>这里这三个值只能写数据类型，不能写具体对象
            new AsyncTask<OutputStream, Integer, Boolean>() {
                //后台执行的任务（所需参数的类型，可变参数数组（内含所需参数的值））
                String Returnresult;

                @Override
                protected Boolean doInBackground(OutputStream... params) {
                    try {
                        Returnresult = exportphone(getApplication());
                        //params[0]=fos=execute所接受的参数

                        //在后台任务中调用自定义的工具方法，该工具方法需要接受一个抽象类的对象（自己设计的抽象类）
                        /**
                         * 这里的第二个参数必须传一个自定义接口的对象，所以必须实现接口中的抽象方法才能创建出对象，相当于这里的这个对象是出生就
                         * 带着两个抽象方法的，把这个带着抽象方法的接口对象传给工具方法后，工具方法拿着传过来的这个接口对象想调用抽象方法，
                         * 就只能用我们创建接口对象时已经实现好的方法，也就是说，它是借用的我们的抽象方法去办事，它自己没有，因为它自己没实现
                         * 这个借用我们的抽象方法的过程就叫做：回调，这种抽象方法就叫做回调函数。
                         * 比如：listener.beforeBackup(cursor.getCount());这句话就是用了我们实现好的方法，而且给我们的方法传进去了一个参数
                         * 它回调时传的参数，我们这边可以立马捕获，因为它的对象本来就是我们的对象的引用
                         * 要始终记得：我们把自定义接口类型的对象（带着两个方法）传递给工具类的方法后，工具方法拿着这个自定义接口类型对象的引用调用抽象方法时，调用的不是工具类
                         * 中的抽象方法体，而是借用的我们已经实现了的抽象方法去做事，它用我们的方法办事，我们这边当然都可以拿到
                         */
                        return true;
                    } catch (Exception e) {
                        e.printStackTrace();
                        return false;
                    }
                }

                //后台任务执行前的操作
                @Override
                protected void onPreExecute() {
                    pDialog = new SweetAlertDialog(MainActivity.this, SweetAlertDialog.PROGRESS_TYPE);
                    pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                    pDialog.setTitleText("Loading");
                    pDialog.setCancelable(true);
                    pDialog.show();
                    super.onPreExecute();
                }

                //后台任务执行后的操作
                @Override
                protected void onPostExecute(Boolean result) {
//                    pDialog.dismiss();
                    if (result) {
                        pDialog.setTitleText("导出完成!")
                                .setContentText(Returnresult)
                                .setConfirmText("OK")
                                .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                    } else {
                        Toast.makeText(getApplicationContext(), "导出失败", Toast.LENGTH_SHORT).show();
                    }
                    super.onPostExecute(result);
                }

                @Override
                protected void onProgressUpdate(Integer... values) {
                    // TODO Auto-generated method stub
                    super.onProgressUpdate(values);
                }
            }.execute();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private String exportphone(Context context) {

        List<TelInfo> info= DB.queryAll();
        int Errornum = 0;
        int Successnum = 0;

        Cursor cursor = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI
                , null, null, null, null);
        ArrayList list = new ArrayList();
        String phoneNumber;
        while (cursor.moveToNext()) {
            phoneNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            list.add(phoneNumber);
            Log.e("phone", phoneNumber);
        }
        if (info != null) {
            Log.e("数据库中有条记录", info.size()+"");
            for (int i = 0; i < info.size(); i++) {
                int flag = 0;
                Log.e("phoneNumber 的值", i + "______++++++++++++++++");
                for (int j = 0; j < list.size(); j++)
                    if (list.get(j).equals(info.get(i).getTel())) {
                        flag = 1;
                        Log.e("标记", info.get(i).getName() + "已在添加");
//                        Toast.makeText(this,info.get(i).get("name")+"导出失败,已存在",Toast.LENGTH_SHORT).show();
                        Errornum++;
//                        Log.e("if语句", list.get(j) + "==== " + info.get(i).get("phone"));
                        break;
                    }
                if (flag == 0) {
                    //创建一个ContentValues
                    ContentValues values = new ContentValues();
                    //先向ContactsContract.RawContacts.CONTENT_URI 中执行插入一个空值，目的 是获取 rawContactId
                    Uri rawContactUri = context.getContentResolver().insert(ContactsContract.RawContacts.CONTENT_URI, values);
                    long rawContactId = ContentUris.parseId(rawContactUri);
                    values.clear();

                    values.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId);
                    //设置内容类型
                    values.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE);
                    //设置联系人的名字
                    values.put(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME, info.get(i).getName());

                    context.getContentResolver().insert(ContactsContract.Data.CONTENT_URI, values);
                    values.clear();
                    values.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId);
                    values.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
                    values.put(ContactsContract.CommonDataKinds.Phone.NUMBER, info.get(i).getTel());
                    context.getContentResolver().insert(ContactsContract.Data.CONTENT_URI, values);
//                    Log.e("标记", info.get(i).get("name") + "正在添加");
//                    Toast.makeText(this,info.get(i).get("name")+"导出成功",Toast.LENGTH_SHORT).show();
                    Successnum++;
                }
            }
//            pDialog.dismiss();

        }
        String result = "成功导入" + Successnum + "条" + "\n" + "失败" + Errornum + "条记录";
        return result;
    }

    private void updateDate() {
        SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();
        initData();
        initview();  // 初始化界面
        pDialog.dismiss();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_gallery) {
            Intent intent = new Intent(this, ScheduleActivity.class);
            startActivity(intent);

        }else if (id == R.id.nav_share) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
