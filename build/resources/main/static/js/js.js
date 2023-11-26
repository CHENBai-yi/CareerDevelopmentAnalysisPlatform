let ws = new WebSocket(`ws://localhost:8080/ws`)
var charts = []
$(window).load(function () {
    $(".loading").fadeOut()
})

/****/
$(document).ready(function () {
    var whei = $(window).width()
    $("html").css({fontSize: whei / 20})
    $(window).resize(function () {
        var whei = $(window).width()
        $("html").css({fontSize: whei / 20})
    });


});


$(window).load(function () {
    $(".loading").fadeOut()
    window.addEventListener('beforeunload', () => localStorage.clear())
})
$(function () {
    init_connect(ws)
    echarts_1()
    echarts_2()
    echarts_3()
    echarts_4()
    echarts_5()
    echarts_6()
    pe01()
    pe02()
    pe03()

    function g() {
        const isValidHexColor = (color) => /^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$/.test(color);
        const isValidRGBColor = (color) => /^rgb\((\s*\d{1,3}\s*,){2}\s*\d{1,3}\s*\)$/.test(color);

        const getRandomHex = () => Math.floor(Math.random() * 256).toString(16).padStart(2, '0');
        const getRandomRGBValue = () => Math.floor(Math.random() * 256);

        const generateRandomHexColor = () => `#${getRandomHex()}${getRandomHex()}${getRandomHex()}`;
        const generateRandomRGBColor = () => `rgb(${getRandomRGBValue()}, ${getRandomRGBValue()}, ${getRandomRGBValue()})`;
        let color;
        // 50% 的概率生成十六进制颜色，50% 的概率生成 RGB 颜色
        if (Math.random() < 0.5) {
            color = generateRandomHexColor();
            while (!isValidHexColor(color)) {
                color = generateRandomHexColor();
            }
        } else {
            color = generateRandomRGBColor();
            while (!isValidRGBColor(color)) {
                color = generateRandomRGBColor();
            }
        }
        return color;
    }

    function generateRandomColor(obj, myColor) {
        let parse = JSON.parse(localStorage.getItem(obj));
        if (!!parse) {
            if (!!parse[myColor]) return parse[myColor]
            else {
                let g1 = g();
                parse[myColor] = g1
                localStorage.setItem(obj, JSON.stringify(parse))
                return g1
            }
        } else {
            let g1 = g();
            localStorage.setItem(obj, JSON.stringify({[myColor]: g1}))
            return g1
        }
    }


    function myCharts(name) {
        let myChart;
        if (charts.some(item => {
            let bl = item.name === name
            if (bl) {
                myChart = item.val
                return bl
            } else return false
        })) {
        } else {
            myChart = echarts.init(document.getElementById(name));
            charts.push({name: name, val: myChart})
        }
        return myChart
    }

    function echarts_1(degree, workYear, month, name = "echarts1") {
        // 基于准备好的dom，初始化echarts实例
        var myChart = myCharts(name)
        let series = []
        let keys = Object.keys(degree);
        let keys2 = Object.keys(workYear);
        keys.forEach((string, index) => {
            series.push({
                "name": string, "type": "bar", "data": degree[string], "barWidth": "15%", "itemStyle": {
                    "normal": {
                        barBorderRadius: 15, color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                            offset: 0, color: generateRandomColor(name, 'c1' + index)
                        }, {
                            offset: 1, color: generateRandomColor(name, 'c1' + index)
                        }]),
                    }
                }, "barGap": "0.2"
            })
        })
        keys2.forEach((string, index) => {
            series.push({
                "name": string, "type": "line", "yAxisIndex": 1,

                "data": workYear[string], lineStyle: {
                    normal: {
                        width: 2
                    },
                }, "itemStyle": {
                    "normal": {
                        "color": generateRandomColor(name, 'c12' + index),

                    }
                }, "smooth": true
            })
        })
        keys.push(...keys2)
        option = {
            tooltip: {
                trigger: 'axis', axisPointer: {type: 'shadow'},
            }, "grid": {
                "top": "20%", "right": "50", "bottom": "20", "left": "30",
            }, legend: {
                data: keys, right: 'center', width: '100%', textStyle: {
                    color: "#fff"
                }, itemWidth: 12, itemHeight: 10,
            },


            "xAxis": [{
                "type": "category", data: month, axisLine: {lineStyle: {color: "rgba(255,255,255,.1)"}}, axisLabel: {
                    textStyle: {color: "rgba(255,255,255,.7)", fontSize: '14',},
                },

            },], "yAxis": [{
                "type": "value", "name": "人数", axisTick: {show: false}, splitLine: {
                    show: false,

                }, "axisLabel": {
                    "show": true, fontSize: 14, color: "rgba(255,255,255,.6)"

                }, axisLine: {
                    min: 0, max: 10, lineStyle: {color: 'rgba(255,255,255,.1)'}
                },//左线色

            }, {
                "type": "value", "name": "增速", "show": true, "axisLabel": {
                    "show": true, fontSize: 14, formatter: "{value} %", color: "rgba(255,255,255,.6)"
                }, axisTick: {show: false}, axisLine: {lineStyle: {color: 'rgba(255,255,255,.1)'}},//右线色
                splitLine: {show: true, lineStyle: {color: 'rgba(255,255,255,.1)'}},//x轴线
            },], "series": series
        };

        myChart.setOption(option, true);
        window.addEventListener("resize", function () {
            myChart.resize();
        });


    }

    function echarts_2(month, data, name = 'echarts2') {
        // 基于准备好的dom，初始化echarts实例
        var myChart = myCharts(name)
        var keys = Object.keys(data)
        var series1 = []

        keys.forEach((key, index) => {
            series1.push({
                name: key, type: 'line', smooth: true, symbol: 'circle', symbolSize: 5, showSymbol: false, lineStyle: {
                    normal: {
                        color: generateRandomColor(name, 'c2' + index), width: 2
                    }
                }, areaStyle: {
                    normal: {
                        color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                            offset: 0, color: generateRandomColor(name, 'c2' + index)
                        }, {
                            offset: 1, color: generateRandomColor(name, 'c2' + index)
                        }], false), shadowColor: 'rgba(0, 0, 0, 0.1)',
                    }
                }, itemStyle: {
                    normal: {
                        color: generateRandomColor(name, 'c2' + index),
                        borderColor: generateRandomColor(name, 'c2' + index),
                        borderWidth: 12
                    }
                }, data: data[key]

            })
        })

        option = {
            tooltip: {
                trigger: 'axis', axisPointer: {type: 'shadow'}, // formatter:'{c}' ,
            }, grid: {
                left: '0', top: '30', right: '10', bottom: '-20', containLabel: true
            }, legend: {
                data: keys, right: 'center', top: 0, textStyle: {
                    color: "#fff"
                }, itemWidth: 12, itemHeight: 10, // itemGap: 35
            },

            xAxis: [{
                type: 'category', boundaryGap: false, axisLabel: {
                    rotate: -90, textStyle: {
                        color: "rgba(255,255,255,.6)", fontSize: 14,

                    },
                }, axisLine: {
                    lineStyle: {
                        color: 'rgba(255,255,255,.1)'
                    }

                },

                data: month

            }, {

                axisPointer: {show: false}, axisLine: {show: false}, position: 'bottom', offset: 20,


            }],

            yAxis: [{
                type: 'value', axisTick: {show: false}, // splitNumber: 6,
                axisLine: {
                    lineStyle: {
                        color: 'rgba(255,255,255,.1)'
                    }
                }, axisLabel: {
                    formatter: "{value} %", textStyle: {
                        color: "rgba(255,255,255,.6)", fontSize: 14,
                    },
                },

                splitLine: {
                    lineStyle: {
                        color: 'rgba(255,255,255,.1)'
                    }
                }
            }], series: series1
        };

        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option, true);
        window.addEventListener("resize", function () {
            myChart.resize();
        });
    }

    function echarts_3(name = 'echarts3') {
        // 基于准备好的dom，初始化echarts实例
        var myChart = myCharts(name)
        option = {
            tooltip: {
                trigger: 'axis', axisPointer: { // 坐标轴指示器，坐标轴触发有效
                    type: 'shadow' // 默认为直线，可选为：'line' | 'shadow'
                }
            }, legend: {
                data: ['字段1', '字段2', '字段3'], right: 'center', top: 0, textStyle: {
                    color: "#fff"
                }, itemWidth: 12, itemHeight: 10, // itemGap: 35
            }, grid: {
                left: '0', right: '20', bottom: '0', top: '15%', containLabel: true
            }, xAxis: {
                type: 'category',
                data: ['字段1', '字段2', '字段3', '字段3', '字段4', '字段5', '字段6', '字段7', '字段8', '字段9'],
                axisLine: {
                    lineStyle: {
                        color: 'white'

                    }
                },
                axisLabel: {
                    //rotate:-90,
                    formatter: function (value) {
                        return value.split("").join("\n");
                    }, textStyle: {
                        color: "rgba(255,255,255,.6)", fontSize: 14,
                    }
                },
                axisLine: {
                    lineStyle: {
                        color: 'rgba(255,255,255,0.3)'
                    }
                },
            },

            yAxis: {
                type: 'value', splitNumber: 4, axisTick: {show: false}, splitLine: {
                    show: true, lineStyle: {
                        color: 'rgba(255,255,255,0.1)'
                    }
                }, axisLabel: {
                    textStyle: {
                        color: "rgba(255,255,255,.6)", fontSize: 14,
                    }
                }, axisLine: {show: false},
            },

            series: [{
                name: '字段1', type: 'bar', stack: 'a', barWidth: '30', barGap: 0, itemStyle: {
                    normal: {
                        color: '#8bd46e',
                    }
                }, data: [331, 497, 440, 81, 163, 366, 57, 188, 172, 2295]
            }, {
                name: '字段2', type: 'bar', stack: 'a', barWidth: '30', barGap: 0, itemStyle: {
                    normal: {
                        color: '#f5804d', barBorderRadius: 0,
                    }
                }, data: [48, -97, 56, -59, 90, 98, 64, 61, -8, 253]
            }, {
                name: '字段3', type: 'bar', stack: 'a', barWidth: '30', barGap: 0, itemStyle: {
                    normal: {
                        color: '#248ff7', barBorderRadius: 0,
                    }
                }, data: [-13, -21, -112, 5, 0, -5, 72, -3, 8, -69]
            }]
        };

        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option, true);
        window.addEventListener("resize", function () {
            myChart.resize();
        });
    }

    function echarts_5(education, work, name = 'echarts5') {
        // 基于准备好的dom，初始化echarts实例
        var myChart = myCharts(name)
        option = {
            tooltip: {
                trigger: 'axis', axisPointer: {type: 'shadow'},
            }, "grid": {
                "top": "15%", "right": "10%", "bottom": "20", "left": "10%",
            },
            legend: {
                data: ['学历', '工作经验'],
                right: 'center',
                top: 0,
                textStyle: {
                    color: "#fff"
                },
                itemWidth: 12,
                itemHeight: 10,
            },
            "xAxis": [
                {
                    "type": "category",

                    data: education.map(item => item.name),
                    axisLine: {lineStyle: {color: "rgba(255,255,255,.1)"}},
                    axisLabel: {
                        textStyle: {color: "rgba(255,255,255,.7)", fontSize: '14',},
                    },

                },
            ],
            "yAxis": [
                {
                    "type": "value",
                    "name": "人数",
                    splitLine: {show: false},
                    axisTick: {show: false},
                    "axisLabel": {
                        "show": true,
                        color: "rgba(255,255,255,.6)"

                    },
                    axisLine: {lineStyle: {color: 'rgba(255,255,255,.1)'}},//左线色

                },
                {
                    "type": "value",
                    "name": "比例",
                    "show": true,
                    axisTick: {show: false},
                    "axisLabel": {
                        "show": true,
                        formatter: "{value}",
                        color: "rgba(255,255,255,.6)"
                    },
                    axisLine: {lineStyle: {color: 'rgba(255,255,255,.1)'}},//右线色
                    splitLine: {show: true, lineStyle: {color: 'rgba(255,255,255,.1)'}},//x轴线
                },
            ],

            "series": [


                {
                    "name": "学历",
                    "type": "bar",
                    "data": education.map(item => item.value),
                    "barWidth": "20%",

                    "itemStyle": {
                        "normal": {
                            barBorderRadius: 15,
                            color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                                offset: 0,
                                color: generateRandomColor(name, 'c51')
                            }, {
                                offset: 1,
                                color: generateRandomColor(name, 'c51')
                            }]),
                        }
                    },
                    "barGap": "0"
                },
                // education.map((eduItem, index) => {
                //     const correspondingWorkItem = work[index];
                //     return correspondingWorkItem ? (correspondingWorkItem.value / eduItem.value).toFixed(2) : 1;
                // }),
                {
                    "name": "工作经验",
                    "type": "line",
                    "yAxisIndex": 1,
                    "data": work.map(item => item.value),
                    lineStyle: {
                        normal: {
                            width: 2
                        },
                    },
                    "itemStyle": {
                        "normal": {
                            "color": generateRandomColor(name, 'c52'),

                        }
                    },
                    "smooth": true
                }
            ]
        };

        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option, true);
        window.addEventListener("resize", function () {
            myChart.resize();
        });
    }

    function echarts_4(yl, data, name = 'echarts4') {
        // 基于准备好的dom，初始化echarts实例
        var myChart = myCharts(name)
        var myColor = ['#eb2100', '#eb3600', '#d0570e', '#d0a00e', '#34da62', '#00e9db', '#00c0e9', '#0096f3'];
        option = {
            grid: {
                left: '2%', top: '1%', right: '5%', bottom: '0%', containLabel: true
            }, xAxis: [{
                show: false,
            }], yAxis: [{
                axisTick: 'none', axisLine: 'none', offset: '7', axisLabel: {
                    textStyle: {
                        color: 'rgba(255,255,255,.6)', fontSize: '14',
                    }
                }, data: yl

            }, //     {
                //     axisTick: 'none',
                //     axisLine: 'none',
                //     axisLabel: {
                //         textStyle: {
                //             color: 'rgba(255,255,255,.6)',
                //             fontSize: '14',
                //         }
                //     },
                //     data: [1514, 1619, 1623, 1968, 2158, 2456, 3506, 4664, 8390]
                //
                // },
                {
                    name: '单位：件', nameGap: '50', nameTextStyle: {
                        color: 'rgba(255,255,255,.6)', fontSize: '16',
                    }, axisLine: {
                        lineStyle: {
                            color: 'rgba(0,0,0,0)'
                        }
                    }, data: [],
                }], series: [{
                name: '条', type: 'bar', yAxisIndex: 0, data: data, label: {
                    normal: {
                        show: true, position: 'right', formatter: function (param) {
                            return param.value + '人次';
                        }, textStyle: {
                            color: 'rgba(255,255,255,.8)', fontSize: '12',
                        }
                    }
                }, barWidth: 15, itemStyle: {
                    normal: {
                        color: new echarts.graphic.LinearGradient(1, 0, 0, 0, [{
                            offset: 0, color: generateRandomColor(name, 'c31')
                        }, {
                            offset: 1, color: generateRandomColor(name, 'c32')
                        }]), barBorderRadius: 15,
                    }
                }, z: 2
            }, {
                name: '白框',
                type: 'bar',
                yAxisIndex: 1,
                barGap: '-100%',
                data: [99.5, 99.5, 99.5, 99.5, 99.5, 99.5, 99.5, 99.5, 99.5, 99.5],
                barWidth: 15,
                itemStyle: {
                    normal: {
                        color: 'rgba(0,0,0,.2)', barBorderRadius: 15,
                    }
                },
                z: 1
            }]
        };


        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option, true);
        window.addEventListener("resize", function () {
            myChart.resize();
        });
    }

    function echarts_6(data, name = 'echarts6') {
        // 基于准备好的dom，初始化echarts实例
        var myChart = myCharts(name)

        // option = {
        //     title: {
        //         text: '5132',
        //         subtext: '总体',
        //         x: 'center',
        //         y: '40%',
        //         textStyle: {
        //             color: '#fff',
        //             fontSize: 22,
        //             lineHeight: 10,
        //         },
        //         subtextStyle: {
        //             color: '#90979c',
        //             fontSize: 16,
        //             lineHeight: 10,
        //
        //         },
        //     },
        //     tooltip: {
        //         trigger: 'item',
        //         formatter: "{b} : {c} ({d}%)"
        //     },
        //
        //     visualMap: {
        //         show: false,
        //         min: 500,
        //         max: 600,
        //         inRange: {
        //             //colorLightness: [0, 1]
        //         }
        //     },
        //     series: [{
        //         name: '访问来源',
        //         type: 'pie',
        //         radius: ['50%', '70%'],
        //         center: ['50%', '50%'],
        //         color: ['rgb(131,249,103)', '#FBFE27', '#FE5050', '#1DB7E5'], //'#FBFE27','rgb(11,228,96)','#FE5050'
        //         data: data.sort(function (a, b) {
        //             return a.value - b.value
        //         }),
        //         roseType: 'radius',
        //
        //         label: {
        //             normal: {
        //                 formatter: ['{c|{c}万}', '{b|{b}}'].join('\n'),
        //                 rich: {
        //                     c: {
        //                         color: 'rgb(241,246,104)',
        //                         fontSize: 20,
        //                         fontWeight: 'bold',
        //                         lineHeight: 5
        //                     },
        //                     b: {
        //                         color: 'rgb(98,137,169)',
        //                         fontSize: 14,
        //                         height: 44
        //                     },
        //                 },
        //             }
        //         },
        //         labelLine: {
        //             normal: {
        //                 lineStyle: {
        //                     color: 'rgb(98,137,169)',
        //                 },
        //                 smooth: 0.2,
        //                 length: 10,
        //                 length2: 20,
        //
        //             }
        //         }
        //     }]
        // };
        option = {
            tooltip: {
                trigger: 'item', formatter: '{a} <br/>{b} : {c} ({d}%)'
            }, legend: {
                type: 'scroll', orient: 'vertical', textStyle: {
                    color: 'rgb(98,137,169)'
                }, right: 10, top: 20, bottom: 20, data: data.map(entry => entry.name), selected: data.selected
            }, series: [{
                name: '学历',
                type: 'pie',
                radius: '55%',
                center: ['40%', '50%'],
                color: data.map((value, index) => generateRandomColor(name, "c6" + index)),
                data: data,
                emphasis: {
                    itemStyle: {
                        shadowBlur: 10, shadowOffsetX: 0, shadowColor: 'rgb(98,137,169)'
                    }
                }
            },]
        };

        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option, true);
        window.addEventListener("resize", function () {
            myChart.resize();
        });
    }


    function pe01() {
        // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('pe01'));
        var txt = 81
        option = {
            title: {
                text: txt + '%', x: 'center', y: 'center', textStyle: {
                    fontWeight: 'normal', color: '#fff', fontSize: '18'
                }
            }, color: 'rgba(255,255,255,.3)',

            series: [{
                name: 'Line 1', type: 'pie', clockWise: true, radius: ['65%', '80%'], itemStyle: {
                    normal: {
                        label: {
                            show: false
                        }, labelLine: {
                            show: false
                        }
                    }
                }, hoverAnimation: false, data: [{
                    value: txt, name: '已使用', itemStyle: {
                        normal: {
                            color: '#eaff00', label: {
                                show: false
                            }, labelLine: {
                                show: false
                            }
                        }
                    }
                }, {
                    name: '未使用', value: 100 - txt
                }]
            }]
        };
        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option, true);
        window.addEventListener("resize", function () {
            myChart.resize();
        });
    }

    function pe02() {
        // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('pe02'));
        var txt = 17
        option = {
            title: {
                text: txt + '%', x: 'center', y: 'center', textStyle: {
                    fontWeight: 'normal', color: '#fff', fontSize: '18'
                }
            }, color: 'rgba(255,255,255,.3)',

            series: [{
                name: 'Line 1', type: 'pie', clockWise: true, radius: ['65%', '80%'], itemStyle: {
                    normal: {
                        label: {
                            show: false
                        }, labelLine: {
                            show: false
                        }
                    }
                }, hoverAnimation: false, data: [{
                    value: txt, name: '已使用', itemStyle: {
                        normal: {
                            color: '#ea4d4d', label: {
                                show: false
                            }, labelLine: {
                                show: false
                            }
                        }
                    }
                }, {
                    name: '未使用', value: 100 - txt
                }]
            }]
        };
        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option, true);
        window.addEventListener("resize", function () {
            myChart.resize();
        });
    }

    function pe03() {
        // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('pe03'));
        var txt = 2
        option = {
            title: {
                text: txt + '%', x: 'center', y: 'center', textStyle: {
                    fontWeight: 'normal', color: '#fff', fontSize: '18'
                }
            }, color: 'rgba(255,255,255,.3)',

            series: [{
                name: 'Line 1', type: 'pie', clockWise: true, radius: ['65%', '80%'], itemStyle: {
                    normal: {
                        label: {
                            show: false
                        }, labelLine: {
                            show: false
                        }
                    }
                }, hoverAnimation: false, data: [{
                    value: txt, name: '已使用', itemStyle: {
                        normal: {
                            color: '#395ee6', label: {
                                show: false
                            }, labelLine: {
                                show: false
                            }
                        }
                    }
                }, {
                    name: '未使用', value: 100 - txt
                }]
            }]
        };
        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option, true);
        window.addEventListener("resize", function () {
            myChart.resize();
        });
    }

    function init_connect(ws) {

        // WebSocket构造函数，创建WebSocket对象

// 连接成功后的回调函数
        ws.onopen = function (params) {
            console.log('客户端连接成功')
            // 向服务器发送消息
            ws.send('hello')
        };

// 从服务器接受到信息时的回调函数
        ws.onmessage = function (e) {
            // console.log(e.data)
            if (!!e.data) {
                let d = JSON.parse(e.data)

                if (!!d.c1) {
                    let c1 = d.c1
                    echarts_1(c1.data.degree, c1.data.workYear, c1.months)
                }
                if (!!d.c2) {
                    let c2 = d.c2
                    echarts_2(c2.months, c2.data)
                }
                if (!!d.c6) {
                    echarts_6(d.c6)
                }
                if (!!d.c4) {
                    let c4 = d.c4
                    echarts_4(c4.yl, c4.data)
                }
                if (!!d.c5) {
                    let c5 = d.c5
                    echarts_5(c5.education, c5.work)
                }

                if (!!d.tableData) {
                    let tableData = d.tableData
                    let tableData1 = $("#tableData tbody");
                    tableData1.empty();
                    tableData1
                        .append(tableData.map(item =>
                            `
                            <tr>
                            <td title="${item.companyname}">${item.companyname}</td>
                            <td title="${item.companytypestring}">${item.companytypestring}</td>
                            <td title="${item.companysizestring}">${item.companysizestring}</td>
                            <td title="${item.jobname}"><a href="${item.jobhref}">${item.jobname}</a></td>
                            <td title="${item.providesalarystring}">${item.providesalarystring}</td>
                            <td title="${item.jobareastring}">${item.jobareastring}</td>
                            <td title="${item.hrisonline}">${item.hrisonline}</td>
                            <td title="${item.hrlabels}">${item.hrlabels}</td>
                        </tr>
                            `
                        ))
                }
                echarts_3()

            }
        };

// 连接关闭后的回调函数
        ws.onclose = function (evt) {
            console.log("关闭客户端连接");
        };

// 连接失败后的回调函数
        ws.onerror = function (evt) {
            console.log("连接失败了");
        };


// 监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，这样服务端会抛异常。
        window.onbeforeunload = function () {
            ws.close();
        }
        return ws;
    }
})




























