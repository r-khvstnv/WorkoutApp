# Fitness Assistant

![Made by](https://img.shields.io/badge/Made%20by-r--khvstnv-orange)
![API](https://img.shields.io/badge/API-21%2B-brightgreen)
![GitHub top language](https://img.shields.io/github/languages/top/r-khvstnv/WorkoutApp)
![Lines of code](https://img.shields.io/tokei/lines/github/r-khvstnv/WorkoutApp)
![GitHub issues](https://img.shields.io/github/issues/r-khvstnv/WorkoutApp)

<a href="https://play.google.com/store/apps/details?id=com.rssll971.fitnessassistantapp"><img alt="Get it on Google Play" src="https://play.google.com/intl/en_us/badges/images/generic/en-play-badge.png" height=60px /></a>

<br/>

![image](i_previews/logo_long_2.png)
_ _ _ 

## Introductions

Fitness Assistant provides the user platform whereby he can train using his exercises and track the result. The Project is based on **a multi-module architecture** using modern android development technologies.
<br/><br/>

## Previews

![image](i_previews/github_preview.png)
_ _ _ 
<br/><br/>

## Technology Overview
### Modules Structure
![image](i_previews/modules_structure.png)
- **core** - Elements and Technological Solutions used by project modules everywhere
- **core-data** - Database and Data Class Models
- **core-theme** - App Theme and XML styles
- **feature-N** - Functional part of Application
_ _ _

### Dagger Dependency Graph
![image](i_previews/dagger_deps.png)
_ _ _

### Navigation Graph
![image](i_previews/navigation_graph.png)

- ___repository*___ - For data transferring between **OptionSecondFragment** and **WorkoutFragment** are used StatisticEntity and RepositoryStatistic respectively. **OptionSecondFragment** saves all customized options by the user and selected exercises. **WorkoutFragment** requests the Latest Row from Statistic Table and then sets up the workout.

- ___nested-grapgh**___ - **OptionFirstFragment** and **OptionSecondFragment** are used shared viewModel **(OptionsViewModel)**. For this reason, the graph is used for viewModel's lifecycle control.
_ _ _
<br/>

## Technology Stack
- **Kotlin**
- **Jetpack**
	- Lifecycle
	- LiveData
	- ViewModel
	- Room
	- Navigation Component & safeArgs
- **Dagger2**
	- Multibinding
	- Scopes
- **Firebase**
	- Performance Monitoring
	- Crashlytics 
	- Analytics
- **Admob**
- **Glide**
_ _ _
<br/>
