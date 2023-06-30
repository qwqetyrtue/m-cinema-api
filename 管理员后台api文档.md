# M-Cinema


**简介**:M-Cinema


**HOST**:localhost:8080


**联系人**:sharo


**Version**:v1.0


**接口路径**:/v2/api-docs?group=管理员后台api文档


[TOC]






# 文件操作接口


## 头像上传


**接口地址**:`/api/admin/resource/upload/avatar`


**请求方式**:`POST`


**请求数据类型**:`multipart/form-data`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|file|file|formData|false|file||
|rename|rename|query|false|string||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|HttpRes|
|201|Created||
|401|Unauthorized||
|403|Forbidden||
|404|Not Found||


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|code||integer(int32)|integer(int32)|
|data||object||
|msg||string||


**响应示例**:
```javascript
{
	"code": 0,
	"data": {},
	"msg": ""
}
```


## 文档上传


**接口地址**:`/api/admin/resource/upload/document`


**请求方式**:`POST`


**请求数据类型**:`multipart/form-data`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|file|file|formData|false|file||
|rename|rename|query|false|string||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|HttpRes|
|201|Created||
|401|Unauthorized||
|403|Forbidden||
|404|Not Found||


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|code||integer(int32)|integer(int32)|
|data||object||
|msg||string||


**响应示例**:
```javascript
{
	"code": 0,
	"data": {},
	"msg": ""
}
```


# 管理员对影厅的操作接口


## 删除影厅


**接口地址**:`/api/admin/hall/delete`


**请求方式**:`POST`


**请求数据类型**:`application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求示例**:


```javascript
{
  "createTime": "",
  "createTimeDown": "",
  "createTimeUp": "",
  "deleted": true,
  "hallId": 0,
  "hallType": "",
  "name": "",
  "seatArrange": "",
  "seatNum": 0,
  "seatNumDown": 0,
  "seatNumUp": 0
}
```


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|delete|delete|body|true|HallRec|HallRec|
|&emsp;&emsp;createTime|||false|string(date-time)||
|&emsp;&emsp;createTimeDown|||false|string(date-time)||
|&emsp;&emsp;createTimeUp|||false|string(date-time)||
|&emsp;&emsp;deleted|||false|boolean||
|&emsp;&emsp;hallId|||false|integer(int64)||
|&emsp;&emsp;hallType|||false|string||
|&emsp;&emsp;name|||false|string||
|&emsp;&emsp;seatArrange|||false|string||
|&emsp;&emsp;seatNum|||false|integer(int32)||
|&emsp;&emsp;seatNumDown|||false|integer(int32)||
|&emsp;&emsp;seatNumUp|||false|integer(int32)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|HttpRes|
|201|Created||
|401|Unauthorized||
|403|Forbidden||
|404|Not Found||


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|code||integer(int32)|integer(int32)|
|data||object||
|msg||string||


**响应示例**:
```javascript
{
	"code": 0,
	"data": {},
	"msg": ""
}
```


## 添加影厅


**接口地址**:`/api/admin/hall/insert`


**请求方式**:`POST`


**请求数据类型**:`application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求示例**:


```javascript
{
  "createTime": "",
  "createTimeDown": "",
  "createTimeUp": "",
  "deleted": true,
  "hallId": 0,
  "hallType": "",
  "name": "",
  "seatArrange": "",
  "seatNum": 0,
  "seatNumDown": 0,
  "seatNumUp": 0
}
```


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|insert|insert|body|true|HallRec|HallRec|
|&emsp;&emsp;createTime|||false|string(date-time)||
|&emsp;&emsp;createTimeDown|||false|string(date-time)||
|&emsp;&emsp;createTimeUp|||false|string(date-time)||
|&emsp;&emsp;deleted|||false|boolean||
|&emsp;&emsp;hallId|||false|integer(int64)||
|&emsp;&emsp;hallType|||false|string||
|&emsp;&emsp;name|||false|string||
|&emsp;&emsp;seatArrange|||false|string||
|&emsp;&emsp;seatNum|||false|integer(int32)||
|&emsp;&emsp;seatNumDown|||false|integer(int32)||
|&emsp;&emsp;seatNumUp|||false|integer(int32)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|HttpRes|
|201|Created||
|401|Unauthorized||
|403|Forbidden||
|404|Not Found||


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|code||integer(int32)|integer(int32)|
|data||object||
|msg||string||


**响应示例**:
```javascript
{
	"code": 0,
	"data": {},
	"msg": ""
}
```


## 查询影厅列表


**接口地址**:`/api/admin/hall/list`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求示例**:


```javascript
{
  "createTime": "",
  "createTimeDown": "",
  "createTimeUp": "",
  "deleted": true,
  "hallId": 0,
  "hallType": "",
  "name": "",
  "seatArrange": "",
  "seatNum": 0,
  "seatNumDown": 0,
  "seatNumUp": 0
}
```


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|pageNum||query|false|integer(int32)||
|pageSize||query|false|integer(int32)||
|filter|filter|body|false|HallRec|HallRec|
|&emsp;&emsp;createTime|||false|string(date-time)||
|&emsp;&emsp;createTimeDown|||false|string(date-time)||
|&emsp;&emsp;createTimeUp|||false|string(date-time)||
|&emsp;&emsp;deleted|||false|boolean||
|&emsp;&emsp;hallId|||false|integer(int64)||
|&emsp;&emsp;hallType|||false|string||
|&emsp;&emsp;name|||false|string||
|&emsp;&emsp;seatArrange|||false|string||
|&emsp;&emsp;seatNum|||false|integer(int32)||
|&emsp;&emsp;seatNumDown|||false|integer(int32)||
|&emsp;&emsp;seatNumUp|||false|integer(int32)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|HttpRes|
|401|Unauthorized||
|403|Forbidden||
|404|Not Found||


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|code||integer(int32)|integer(int32)|
|data||object||
|msg||string||


**响应示例**:
```javascript
{
	"code": 0,
	"data": {},
	"msg": ""
}
```


## 根据电影ID查询单个影厅信息


**接口地址**:`/api/admin/hall/one`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求示例**:


```javascript
{
  "createTime": "",
  "createTimeDown": "",
  "createTimeUp": "",
  "deleted": true,
  "hallId": 0,
  "hallType": "",
  "name": "",
  "seatArrange": "",
  "seatNum": 0,
  "seatNumDown": 0,
  "seatNumUp": 0
}
```


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|hall|hall|body|true|HallRec|HallRec|
|&emsp;&emsp;createTime|||false|string(date-time)||
|&emsp;&emsp;createTimeDown|||false|string(date-time)||
|&emsp;&emsp;createTimeUp|||false|string(date-time)||
|&emsp;&emsp;deleted|||false|boolean||
|&emsp;&emsp;hallId|||false|integer(int64)||
|&emsp;&emsp;hallType|||false|string||
|&emsp;&emsp;name|||false|string||
|&emsp;&emsp;seatArrange|||false|string||
|&emsp;&emsp;seatNum|||false|integer(int32)||
|&emsp;&emsp;seatNumDown|||false|integer(int32)||
|&emsp;&emsp;seatNumUp|||false|integer(int32)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|HttpRes|
|401|Unauthorized||
|403|Forbidden||
|404|Not Found||


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|code||integer(int32)|integer(int32)|
|data||object||
|msg||string||


**响应示例**:
```javascript
{
	"code": 0,
	"data": {},
	"msg": ""
}
```


## 修改影厅信息


**接口地址**:`/api/admin/hall/update`


**请求方式**:`POST`


**请求数据类型**:`application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求示例**:


```javascript
{
  "createTime": "",
  "deleted": true,
  "hallId": 0,
  "hallType": "",
  "name": "",
  "seatArrange": "",
  "seatNum": 0
}
```


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|update|update|body|true|HallBase|HallBase|
|&emsp;&emsp;createTime|||false|string(date-time)||
|&emsp;&emsp;deleted|||false|boolean||
|&emsp;&emsp;hallId|||false|integer(int64)||
|&emsp;&emsp;hallType|||false|string||
|&emsp;&emsp;name|||false|string||
|&emsp;&emsp;seatArrange|||false|string||
|&emsp;&emsp;seatNum|||false|integer(int32)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|HttpRes|
|201|Created||
|401|Unauthorized||
|403|Forbidden||
|404|Not Found||


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|code||integer(int32)|integer(int32)|
|data||object||
|msg||string||


**响应示例**:
```javascript
{
	"code": 0,
	"data": {},
	"msg": ""
}
```


# 管理员对用户的操作接口


## 删除用户


**接口地址**:`/api/admin/user/delete`


**请求方式**:`POST`


**请求数据类型**:`application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求示例**:


```javascript
{
  "age": 0,
  "ageDown": 0,
  "ageUp": 0,
  "avatar": "",
  "createTime": "",
  "createTimeDown": "",
  "createTimeUp": "",
  "deleted": true,
  "gender": 0,
  "name": "",
  "phone": "",
  "pwd": "",
  "sign": "",
  "userId": 0
}
```


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|delete|delete|body|true|UserRec|UserRec|
|&emsp;&emsp;age|||false|integer(int32)||
|&emsp;&emsp;ageDown|||false|integer(int32)||
|&emsp;&emsp;ageUp|||false|integer(int32)||
|&emsp;&emsp;avatar|||false|string||
|&emsp;&emsp;createTime|||false|string(date-time)||
|&emsp;&emsp;createTimeDown|||false|string(date-time)||
|&emsp;&emsp;createTimeUp|||false|string(date-time)||
|&emsp;&emsp;deleted|||false|boolean||
|&emsp;&emsp;gender|||false|integer(int32)||
|&emsp;&emsp;name|||false|string||
|&emsp;&emsp;phone|||false|string||
|&emsp;&emsp;pwd|||false|string||
|&emsp;&emsp;sign|||false|string||
|&emsp;&emsp;userId|||false|integer(int64)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|HttpRes|
|201|Created||
|401|Unauthorized||
|403|Forbidden||
|404|Not Found||


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|code||integer(int32)|integer(int32)|
|data||object||
|msg||string||


**响应示例**:
```javascript
{
	"code": 0,
	"data": {},
	"msg": ""
}
```


## 添加用户


**接口地址**:`/api/admin/user/insert`


**请求方式**:`POST`


**请求数据类型**:`application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求示例**:


```javascript
{
  "age": 0,
  "ageDown": 0,
  "ageUp": 0,
  "avatar": "",
  "createTime": "",
  "createTimeDown": "",
  "createTimeUp": "",
  "deleted": true,
  "gender": 0,
  "name": "",
  "phone": "",
  "pwd": "",
  "sign": "",
  "userId": 0
}
```


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|insert|insert|body|true|UserRec|UserRec|
|&emsp;&emsp;age|||false|integer(int32)||
|&emsp;&emsp;ageDown|||false|integer(int32)||
|&emsp;&emsp;ageUp|||false|integer(int32)||
|&emsp;&emsp;avatar|||false|string||
|&emsp;&emsp;createTime|||false|string(date-time)||
|&emsp;&emsp;createTimeDown|||false|string(date-time)||
|&emsp;&emsp;createTimeUp|||false|string(date-time)||
|&emsp;&emsp;deleted|||false|boolean||
|&emsp;&emsp;gender|||false|integer(int32)||
|&emsp;&emsp;name|||false|string||
|&emsp;&emsp;phone|||false|string||
|&emsp;&emsp;pwd|||false|string||
|&emsp;&emsp;sign|||false|string||
|&emsp;&emsp;userId|||false|integer(int64)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|HttpRes|
|201|Created||
|401|Unauthorized||
|403|Forbidden||
|404|Not Found||


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|code||integer(int32)|integer(int32)|
|data||object||
|msg||string||


**响应示例**:
```javascript
{
	"code": 0,
	"data": {},
	"msg": ""
}
```


## 查询用户列表


**接口地址**:`/api/admin/user/list`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求示例**:


```javascript
{
  "age": 0,
  "ageDown": 0,
  "ageUp": 0,
  "avatar": "",
  "createTime": "",
  "createTimeDown": "",
  "createTimeUp": "",
  "deleted": true,
  "gender": 0,
  "name": "",
  "phone": "",
  "pwd": "",
  "sign": "",
  "userId": 0
}
```


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|filter|filter|body|true|UserRec|UserRec|
|&emsp;&emsp;age|||false|integer(int32)||
|&emsp;&emsp;ageDown|||false|integer(int32)||
|&emsp;&emsp;ageUp|||false|integer(int32)||
|&emsp;&emsp;avatar|||false|string||
|&emsp;&emsp;createTime|||false|string(date-time)||
|&emsp;&emsp;createTimeDown|||false|string(date-time)||
|&emsp;&emsp;createTimeUp|||false|string(date-time)||
|&emsp;&emsp;deleted|||false|boolean||
|&emsp;&emsp;gender|||false|integer(int32)||
|&emsp;&emsp;name|||false|string||
|&emsp;&emsp;phone|||false|string||
|&emsp;&emsp;pwd|||false|string||
|&emsp;&emsp;sign|||false|string||
|&emsp;&emsp;userId|||false|integer(int64)||
|pageNum||query|false|integer(int32)||
|pageSize||query|false|integer(int32)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|HttpRes|
|401|Unauthorized||
|403|Forbidden||
|404|Not Found||


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|code||integer(int32)|integer(int32)|
|data||object||
|msg||string||


**响应示例**:
```javascript
{
	"code": 0,
	"data": {},
	"msg": ""
}
```


## 根据用户ID查询单个用户信息


**接口地址**:`/api/admin/user/one`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求示例**:


```javascript
{
  "age": 0,
  "ageDown": 0,
  "ageUp": 0,
  "avatar": "",
  "createTime": "",
  "createTimeDown": "",
  "createTimeUp": "",
  "deleted": true,
  "gender": 0,
  "name": "",
  "phone": "",
  "pwd": "",
  "sign": "",
  "userId": 0
}
```


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|select|select|body|true|UserRec|UserRec|
|&emsp;&emsp;age|||false|integer(int32)||
|&emsp;&emsp;ageDown|||false|integer(int32)||
|&emsp;&emsp;ageUp|||false|integer(int32)||
|&emsp;&emsp;avatar|||false|string||
|&emsp;&emsp;createTime|||false|string(date-time)||
|&emsp;&emsp;createTimeDown|||false|string(date-time)||
|&emsp;&emsp;createTimeUp|||false|string(date-time)||
|&emsp;&emsp;deleted|||false|boolean||
|&emsp;&emsp;gender|||false|integer(int32)||
|&emsp;&emsp;name|||false|string||
|&emsp;&emsp;phone|||false|string||
|&emsp;&emsp;pwd|||false|string||
|&emsp;&emsp;sign|||false|string||
|&emsp;&emsp;userId|||false|integer(int64)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|HttpRes|
|401|Unauthorized||
|403|Forbidden||
|404|Not Found||


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|code||integer(int32)|integer(int32)|
|data||object||
|msg||string||


**响应示例**:
```javascript
{
	"code": 0,
	"data": {},
	"msg": ""
}
```


## 修改用户信息


**接口地址**:`/api/admin/user/update`


**请求方式**:`POST`


**请求数据类型**:`application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求示例**:


```javascript
{
  "age": 0,
  "ageDown": 0,
  "ageUp": 0,
  "avatar": "",
  "createTime": "",
  "createTimeDown": "",
  "createTimeUp": "",
  "deleted": true,
  "gender": 0,
  "name": "",
  "phone": "",
  "pwd": "",
  "sign": "",
  "userId": 0
}
```


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|update|update|body|true|UserRec|UserRec|
|&emsp;&emsp;age|||false|integer(int32)||
|&emsp;&emsp;ageDown|||false|integer(int32)||
|&emsp;&emsp;ageUp|||false|integer(int32)||
|&emsp;&emsp;avatar|||false|string||
|&emsp;&emsp;createTime|||false|string(date-time)||
|&emsp;&emsp;createTimeDown|||false|string(date-time)||
|&emsp;&emsp;createTimeUp|||false|string(date-time)||
|&emsp;&emsp;deleted|||false|boolean||
|&emsp;&emsp;gender|||false|integer(int32)||
|&emsp;&emsp;name|||false|string||
|&emsp;&emsp;phone|||false|string||
|&emsp;&emsp;pwd|||false|string||
|&emsp;&emsp;sign|||false|string||
|&emsp;&emsp;userId|||false|integer(int64)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|HttpRes|
|201|Created||
|401|Unauthorized||
|403|Forbidden||
|404|Not Found||


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|code||integer(int32)|integer(int32)|
|data||object||
|msg||string||


**响应示例**:
```javascript
{
	"code": 0,
	"data": {},
	"msg": ""
}
```


# 管理员对电影的操作接口


## 删除电影


**接口地址**:`/api/admin/film/delete`


**请求方式**:`POST`


**请求数据类型**:`application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求示例**:


```javascript
{
  "actor": "",
  "age": 0,
  "ageDown": 0,
  "ageUp": 0,
  "area": "",
  "createTime": "",
  "createTimeDown": "",
  "createTimeUp": "",
  "deleted": true,
  "filmClassify": "",
  "filmId": 0,
  "filmType": "",
  "name": "",
  "otherInfo": "",
  "synopsis": ""
}
```


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|delete|delete|body|true|FilmRec|FilmRec|
|&emsp;&emsp;actor|||false|string||
|&emsp;&emsp;age|||false|integer(int32)||
|&emsp;&emsp;ageDown|||false|integer(int32)||
|&emsp;&emsp;ageUp|||false|integer(int32)||
|&emsp;&emsp;area|||false|string||
|&emsp;&emsp;createTime|||false|string(date-time)||
|&emsp;&emsp;createTimeDown|||false|string(date-time)||
|&emsp;&emsp;createTimeUp|||false|string(date-time)||
|&emsp;&emsp;deleted|||false|boolean||
|&emsp;&emsp;filmClassify|||false|string||
|&emsp;&emsp;filmId|||false|integer(int64)||
|&emsp;&emsp;filmType|||false|string||
|&emsp;&emsp;name|||false|string||
|&emsp;&emsp;otherInfo|||false|string||
|&emsp;&emsp;synopsis|||false|string||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|HttpRes|
|201|Created||
|401|Unauthorized||
|403|Forbidden||
|404|Not Found||


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|code||integer(int32)|integer(int32)|
|data||object||
|msg||string||


**响应示例**:
```javascript
{
	"code": 0,
	"data": {},
	"msg": ""
}
```


## 添加电影


**接口地址**:`/api/admin/film/insert`


**请求方式**:`POST`


**请求数据类型**:`application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求示例**:


```javascript
{
  "actor": "",
  "age": 0,
  "ageDown": 0,
  "ageUp": 0,
  "area": "",
  "createTime": "",
  "createTimeDown": "",
  "createTimeUp": "",
  "deleted": true,
  "filmClassify": "",
  "filmId": 0,
  "filmType": "",
  "name": "",
  "otherInfo": "",
  "synopsis": ""
}
```


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|insert|insert|body|true|FilmRec|FilmRec|
|&emsp;&emsp;actor|||false|string||
|&emsp;&emsp;age|||false|integer(int32)||
|&emsp;&emsp;ageDown|||false|integer(int32)||
|&emsp;&emsp;ageUp|||false|integer(int32)||
|&emsp;&emsp;area|||false|string||
|&emsp;&emsp;createTime|||false|string(date-time)||
|&emsp;&emsp;createTimeDown|||false|string(date-time)||
|&emsp;&emsp;createTimeUp|||false|string(date-time)||
|&emsp;&emsp;deleted|||false|boolean||
|&emsp;&emsp;filmClassify|||false|string||
|&emsp;&emsp;filmId|||false|integer(int64)||
|&emsp;&emsp;filmType|||false|string||
|&emsp;&emsp;name|||false|string||
|&emsp;&emsp;otherInfo|||false|string||
|&emsp;&emsp;synopsis|||false|string||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|HttpRes|
|201|Created||
|401|Unauthorized||
|403|Forbidden||
|404|Not Found||


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|code||integer(int32)|integer(int32)|
|data||object||
|msg||string||


**响应示例**:
```javascript
{
	"code": 0,
	"data": {},
	"msg": ""
}
```


## 查询电影列表


**接口地址**:`/api/admin/film/list`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求示例**:


```javascript
{
  "actor": "",
  "age": 0,
  "ageDown": 0,
  "ageUp": 0,
  "area": "",
  "createTime": "",
  "createTimeDown": "",
  "createTimeUp": "",
  "deleted": true,
  "filmClassify": "",
  "filmId": 0,
  "filmType": "",
  "name": "",
  "otherInfo": "",
  "synopsis": ""
}
```


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|pageNum||query|false|integer(int32)||
|pageSize||query|false|integer(int32)||
|filter|filter|body|false|FilmRec|FilmRec|
|&emsp;&emsp;actor|||false|string||
|&emsp;&emsp;age|||false|integer(int32)||
|&emsp;&emsp;ageDown|||false|integer(int32)||
|&emsp;&emsp;ageUp|||false|integer(int32)||
|&emsp;&emsp;area|||false|string||
|&emsp;&emsp;createTime|||false|string(date-time)||
|&emsp;&emsp;createTimeDown|||false|string(date-time)||
|&emsp;&emsp;createTimeUp|||false|string(date-time)||
|&emsp;&emsp;deleted|||false|boolean||
|&emsp;&emsp;filmClassify|||false|string||
|&emsp;&emsp;filmId|||false|integer(int64)||
|&emsp;&emsp;filmType|||false|string||
|&emsp;&emsp;name|||false|string||
|&emsp;&emsp;otherInfo|||false|string||
|&emsp;&emsp;synopsis|||false|string||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|HttpRes|
|401|Unauthorized||
|403|Forbidden||
|404|Not Found||


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|code||integer(int32)|integer(int32)|
|data||object||
|msg||string||


**响应示例**:
```javascript
{
	"code": 0,
	"data": {},
	"msg": ""
}
```


## 根据电影ID查询单个电影信息


**接口地址**:`/api/admin/film/one`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求示例**:


```javascript
{
  "actor": "",
  "age": 0,
  "ageDown": 0,
  "ageUp": 0,
  "area": "",
  "createTime": "",
  "createTimeDown": "",
  "createTimeUp": "",
  "deleted": true,
  "filmClassify": "",
  "filmId": 0,
  "filmType": "",
  "name": "",
  "otherInfo": "",
  "synopsis": ""
}
```


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|select|select|body|true|FilmRec|FilmRec|
|&emsp;&emsp;actor|||false|string||
|&emsp;&emsp;age|||false|integer(int32)||
|&emsp;&emsp;ageDown|||false|integer(int32)||
|&emsp;&emsp;ageUp|||false|integer(int32)||
|&emsp;&emsp;area|||false|string||
|&emsp;&emsp;createTime|||false|string(date-time)||
|&emsp;&emsp;createTimeDown|||false|string(date-time)||
|&emsp;&emsp;createTimeUp|||false|string(date-time)||
|&emsp;&emsp;deleted|||false|boolean||
|&emsp;&emsp;filmClassify|||false|string||
|&emsp;&emsp;filmId|||false|integer(int64)||
|&emsp;&emsp;filmType|||false|string||
|&emsp;&emsp;name|||false|string||
|&emsp;&emsp;otherInfo|||false|string||
|&emsp;&emsp;synopsis|||false|string||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|HttpRes|
|401|Unauthorized||
|403|Forbidden||
|404|Not Found||


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|code||integer(int32)|integer(int32)|
|data||object||
|msg||string||


**响应示例**:
```javascript
{
	"code": 0,
	"data": {},
	"msg": ""
}
```


## 修改电影


**接口地址**:`/api/admin/film/update`


**请求方式**:`POST`


**请求数据类型**:`application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求示例**:


```javascript
{
  "actor": "",
  "age": 0,
  "ageDown": 0,
  "ageUp": 0,
  "area": "",
  "createTime": "",
  "createTimeDown": "",
  "createTimeUp": "",
  "deleted": true,
  "filmClassify": "",
  "filmId": 0,
  "filmType": "",
  "name": "",
  "otherInfo": "",
  "synopsis": ""
}
```


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|update|update|body|true|FilmRec|FilmRec|
|&emsp;&emsp;actor|||false|string||
|&emsp;&emsp;age|||false|integer(int32)||
|&emsp;&emsp;ageDown|||false|integer(int32)||
|&emsp;&emsp;ageUp|||false|integer(int32)||
|&emsp;&emsp;area|||false|string||
|&emsp;&emsp;createTime|||false|string(date-time)||
|&emsp;&emsp;createTimeDown|||false|string(date-time)||
|&emsp;&emsp;createTimeUp|||false|string(date-time)||
|&emsp;&emsp;deleted|||false|boolean||
|&emsp;&emsp;filmClassify|||false|string||
|&emsp;&emsp;filmId|||false|integer(int64)||
|&emsp;&emsp;filmType|||false|string||
|&emsp;&emsp;name|||false|string||
|&emsp;&emsp;otherInfo|||false|string||
|&emsp;&emsp;synopsis|||false|string||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|HttpRes|
|201|Created||
|401|Unauthorized||
|403|Forbidden||
|404|Not Found||


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|code||integer(int32)|integer(int32)|
|data||object||
|msg||string||


**响应示例**:
```javascript
{
	"code": 0,
	"data": {},
	"msg": ""
}
```


# 管理员对管理员的操作接口


## 删除管理员


**接口地址**:`/api/admin/admin/delete`


**请求方式**:`POST`


**请求数据类型**:`application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


暂无


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|HttpRes|
|201|Created||
|401|Unauthorized||
|403|Forbidden||
|404|Not Found||


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|code||integer(int32)|integer(int32)|
|data||object||
|msg||string||


**响应示例**:
```javascript
{
	"code": 0,
	"data": {},
	"msg": ""
}
```


## 添加管理员


**接口地址**:`/api/admin/admin/insert`


**请求方式**:`POST`


**请求数据类型**:`application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


暂无


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|HttpRes|
|201|Created||
|401|Unauthorized||
|403|Forbidden||
|404|Not Found||


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|code||integer(int32)|integer(int32)|
|data||object||
|msg||string||


**响应示例**:
```javascript
{
	"code": 0,
	"data": {},
	"msg": ""
}
```


## 查询管理员列表


**接口地址**:`/api/admin/admin/list`


**请求方式**:`POST`


**请求数据类型**:`application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求示例**:


```javascript
{
  "captcha": "",
  "deleted": true,
  "password": "",
  "username": ""
}
```


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|req|req|body|true|AdminRec|AdminRec|
|&emsp;&emsp;captcha|||false|string||
|&emsp;&emsp;deleted|||false|boolean||
|&emsp;&emsp;password|||false|string||
|&emsp;&emsp;username|||false|string||
|pageNum||query|false|integer(int32)||
|pageSize||query|false|integer(int32)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|HttpRes|
|201|Created||
|401|Unauthorized||
|403|Forbidden||
|404|Not Found||


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|code||integer(int32)|integer(int32)|
|data||object||
|msg||string||


**响应示例**:
```javascript
{
	"code": 0,
	"data": {},
	"msg": ""
}
```


## 由管理员ID查询单个管理员信息


**接口地址**:`/api/admin/admin/one`


**请求方式**:`POST`


**请求数据类型**:`application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


暂无


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|HttpRes|
|201|Created||
|401|Unauthorized||
|403|Forbidden||
|404|Not Found||


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|code||integer(int32)|integer(int32)|
|data||object||
|msg||string||


**响应示例**:
```javascript
{
	"code": 0,
	"data": {},
	"msg": ""
}
```


## 修改管理员信息


**接口地址**:`/api/admin/admin/update`


**请求方式**:`POST`


**请求数据类型**:`application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


暂无


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|HttpRes|
|201|Created||
|401|Unauthorized||
|403|Forbidden||
|404|Not Found||


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|code||integer(int32)|integer(int32)|
|data||object||
|msg||string||


**响应示例**:
```javascript
{
	"code": 0,
	"data": {},
	"msg": ""
}
```


# 管理员权限验证接口


## 查看管理员已授权api


**接口地址**:`/api/admin/auth/api`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


暂无


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|HttpRes|
|401|Unauthorized||
|403|Forbidden||
|404|Not Found||


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|code||integer(int32)|integer(int32)|
|data||object||
|msg||string||


**响应示例**:
```javascript
{
	"code": 0,
	"data": {},
	"msg": ""
}
```


## 查看管理员个人信息


**接口地址**:`/api/admin/auth/info`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


暂无


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|HttpRes|
|401|Unauthorized||
|403|Forbidden||
|404|Not Found||


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|code||integer(int32)|integer(int32)|
|data||object||
|msg||string||


**响应示例**:
```javascript
{
	"code": 0,
	"data": {},
	"msg": ""
}
```


## 管理员登录


**接口地址**:`/api/admin/auth/login`


**请求方式**:`POST`


**请求数据类型**:`application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求示例**:


```javascript
{
  "captcha": "",
  "deleted": true,
  "password": "",
  "username": ""
}
```


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|login|login|body|true|AdminRec|AdminRec|
|&emsp;&emsp;captcha|||false|string||
|&emsp;&emsp;deleted|||false|boolean||
|&emsp;&emsp;password|||false|string||
|&emsp;&emsp;username|||false|string||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|HttpRes|
|201|Created||
|401|Unauthorized||
|403|Forbidden||
|404|Not Found||


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|code||integer(int32)|integer(int32)|
|data||object||
|msg||string||


**响应示例**:
```javascript
{
	"code": 0,
	"data": {},
	"msg": ""
}
```


## 管理员退出登录


**接口地址**:`/api/admin/auth/logout`


**请求方式**:`POST`


**请求数据类型**:`application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


暂无


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|HttpRes|
|201|Created||
|401|Unauthorized||
|403|Forbidden||
|404|Not Found||


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|code||integer(int32)|integer(int32)|
|data||object||
|msg||string||


**响应示例**:
```javascript
{
	"code": 0,
	"data": {},
	"msg": ""
}
```


## 生成验证码


**接口地址**:`/api/admin/auth/verify_code`


**请求方式**:`POST`


**请求数据类型**:`application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|type|type|query|true|string||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|HttpRes|
|201|Created||
|401|Unauthorized||
|403|Forbidden||
|404|Not Found||


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|code||integer(int32)|integer(int32)|
|data||object||
|msg||string||


**响应示例**:
```javascript
{
	"code": 0,
	"data": {},
	"msg": ""
}
```


# 页面跳转接口


## indexPageHandler


**接口地址**:`/api/admin/page/index`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


暂无


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|HttpRes|
|401|Unauthorized||
|403|Forbidden||
|404|Not Found||


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|code||integer(int32)|integer(int32)|
|data||object||
|msg||string||


**响应示例**:
```javascript
{
	"code": 0,
	"data": {},
	"msg": ""
}
```