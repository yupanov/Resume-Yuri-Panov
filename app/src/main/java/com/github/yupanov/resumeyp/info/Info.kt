package com.github.yupanov.resumeyp.info

data class Info(val title: String, val description: String, val icon: String) {
}

object infoBase {
    val base = listOf<Info>(
        Info("Skills","Python, JavaScript, C++ (Arduino), MySQL, HTML, CSS, React, NodeJS, Bootstrap, Git",
            "ic_skills"),
//        Info("Contacts", "+5491132147310", "ic_contacts"),
//        Info("Education", "Moscow university of MVD", "ic_education"),
//        Info("Hobby", "Bicycle, tango, weed", "ic_hobby"),
//        Info("Personal presentation", "Let you be you, and others be others", "ic_personal"),
//        Info("Experience", "Freelance", "ic_experience"),
//        Info("Soft skills", "You will love me", "ic_softskills"),
        Info("References", "Brian Nieto", "ic_references")
    )
}

