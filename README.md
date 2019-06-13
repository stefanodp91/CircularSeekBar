# CircularSeekBar 

A framework used to create circular seek bar

---

## Screenshots

![Trestle](https://raw.githubusercontent.com/lawloretienne/Trestle/master/images/Trestle_Screenshot_3.png)

---

## Setup

#### Gradle

` implementation 'it.fourn.android:circularseekbar:0.1'`

#### Maven
```
<dependency>
  <groupId>it.fourn.android</groupId>
  <artifactId>circularseekbar</artifactId>
  <version>0.1</version>
  <type>pom</type>
</dependency>
```

---

## Usage

```xml
  ...
  <it.fourn.android.circularseekbar.CircularSeekBar
      android:id="@+id/seek"
      android:layout_width="wrap_content"
      android:layout_height="wrap_content"
      app:cs_arcRadius="150dp"/>
  ...
```

---


## Customization 

*  `app:cs_arcRadius`           : The radius of the circle. It is a mandatory field and must be chosen wisely. It must be less than the screen size
*  `app:cs_step`                : The increment of the percentual. Default is set to 1
*  `app:cs_progressWidth`       : The width of progress
*  `app:cs_arcWidth`            : The width of base cicle
*  `app:cs_indicatorRadius`     : The radius of the draggable indicator
*  `app:cs_enabled`             : Enable or disable the drag
*  `app:cs_color_list`          : Integer vector used for gradient generation
*  `app:cs_text`                : Text to display to the center of the base cicle
*  `app:cs_text_size`           : Text size
*  `app:cs_text_color`          : Text static color
*  `app:cs_dynamic_text_color`  : Text color changes according to the indicator color
*  `app:cs_text_progress`       : Display the current progress as text


---

## Developed By

Author: Stefano de Pascalis
[](https://it.linkedin.com/in/stefano-de-pascalis-1b51aa6a)

[![Google+](https://upload.wikimedia.org/wikipedia/commons/thumb/4/49/Antu_googleplus.svg/72px-Antu_googleplus.svg.png)](https://plus.google.com/u/1/+StefanoDePascalis)
[![LinkedIn](https://tks.com.au/Images/Home/LinkedIn.png)](https://it.linkedin.com/in/stefano-de-pascalis-1b51aa6a)

---

## License

```
Copyright 2019 stefanodp91.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
