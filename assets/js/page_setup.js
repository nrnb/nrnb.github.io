/**
 * Created by kono on 2014/03/20.
 */
var navbarText =
    '<div class="navbar navbar-inverse navbar-fixed-top">' +
    '<div class="navbar-inner">' +
        '<div class="container">' +
            '<a class="brand" href="index.html">' +
                '<img src="images/NRNB_white_22.png" alt="image"/>' +
            '</a>' +
            '<ul class="nav">' +
                '<li class="dropdown">' +
                    '<a href="#" class="dropdown-toggle" data-toggle="dropdown">About <b class="caret"></b></a>' +
                    '<ul class="dropdown-menu">' +
                        '<li><a href="about-nb.html">What is Network Biology?</a></li>' +
                        '<li><a href="people.html">NRNB Team</a></li>' +
                        '<li><a href="people.html#eac">Advisory Committee</a></li>' +
                        '<li><a href="people.html#partners">Partners</a></li>' +
                        '</li>' +
                    '</ul>' +
                '</li>' +
                '<li class="dropdown">' +
                    '<a href="#" class="dropdown-toggle" data-toggle="dropdown">Research <b class="caret"></b></a>' +
                    '<ul class="dropdown-menu">' +
                        '<li class="dropdown-submenu">' +
                            '<a href="#">Projects</a>' +
                            '<ul class="dropdown-menu" id="projects-menu">' +
                                '<li><a href="projects.html"><b>Projects</b></a></li>' +
                                '<li class="divider"></li>' +
                                '<li>' +
                                    '<a href="projects.html#a-tab">' +
                                    'Theme 1: Differential Networks' +
                                    '</a>' +
                                '</li>' +
                                '<li>' +
                                    '<a href="projects.html#b-tab">' +
                                    'Theme 2: From Descriptive to Predictive' +
                                    '</a>' +
                                '</li>' +
                                '<li>' +
                                    '<a href="projects.html#c-tab">' +
                                    'Theme 3: Multi-scale Network Representations' +
                                    '</a>' +
                                '</li>' +
                            '</ul>' +
                        '</li>' +
                        '<li><a href="https://www.ncbi.nlm.nih.gov/pubmed?term=%22national%20resource%20for%20network%20biology%22" target="_blank">Publications</a></li>' +
                    '</ul>' +
                '</li>' +
                '<li class="dropdown">' +
                    '<a href="#" class="dropdown-toggle" data-toggle="dropdown">Tools <b class="caret"></b></a>' +
                    '<ul class="dropdown-menu" id="tools-menu">' +
                        '<li><a href="tools-wall.html"><b>Tools</b></a></li>' +
                        '<li class="divider"></li>' +
    			'<li><a href="tools.html#amatreader-tab">aMatReader</a></li>' +
                        '<li><a href="tools.html#cbioportal-tab">cBio Portal</a></li>' +  
                        '<li><a href="tools.html#clustermaker-tab">clusterMaker</a></li>' + 
    			'<li><a href="tools.html#cyanimator-tab">CyAnimator</a></li>' +
                        '<li><a href="tools.html#cynitoolbox-tab">Cyni Toolbox</a></li>' +  
                        '<li><a href="tools.html#cytoscape-tab">Cytoscape</a></li>' +
                        '<li><a href="tools.html#cytoscapejs-tab">Cytoscape.js</a></li>' +
                        '<li><a href="tools.html#cytoscapeappstore-tab">Cytoscape App Store</a></li>' +
                        '<li><a href="tools.html#enrichmentmap-tab">Enrichment Map</a></li>' +  
                        '<li><a href="tools.html#genemania-tab">GeneMANIA</a></li>' +  
                        '<li><a href="tools.html#gestodifferent-tab">GeStoDifferent</a></li>' +   
                        '<li><a href="tools.html#mosaic-tab">Mosaic</a></li>' +   
    			'<li><a href="tools.html#mcds-tab">MCDS</a></li>' + 
                        '<li><a href="tools.html#ndex-tab">NDEx</a></li>' +   
                        '<li><a href="tools.html#nexo-tab">NeXO</a></li>' +    
                        '<li><a href="tools.html#noa-tab">NOA</a></li>' +  
                        '<li><a href="tools.html#noctua-tab">Noctua</a></li>' +
                        '<li><a href="tools.html#pathvisio-tab">PathVisio</a></li>' +
                        '<li><a href="tools.html#pihelper-tab">PiHelper</a></li>' +
    			'<li><a href="tools.html#setsapp-tab">setsApp</a></li>' +
    			'<li><a href="tools.html#string-tab">stringApp</a></li>' +
                        '<li><a href="tools.html#wikipathways-tab">WikiPathways</a></li>' +  
                        '<li><a href="tools.html#wordcloud-tab">WordCloud</a></li>' +
                        '<li class="divider"></li>' +
                        '<li><a href="training.html#tutorials-tab">How to Get Started</a></li>' +
                    '</ul>' +
                '</li>' +
                '<li class="dropdown">' +
                    '<a href="#" class="dropdown-toggle" data-toggle="dropdown">Media <b class="caret"></b></a>' +
                    '<ul class="dropdown-menu" id="presentations-menu">' +
                        '<li><a href="https://www.slideshare.net/search/slideshow?searchfrom=header&q=nrnb" target="_blank">NRNB Presentations</a></li>' +
                    '</ul>' +
                '</li>' +
                '<li class="dropdown">' +
                    '<a href="#" class="dropdown-toggle" data-toggle="dropdown">Training <b class="caret"></b></a>' +
                    '<ul class="dropdown-menu" id="training-menu">' +
                        '<li><a href="training.html"><b>Training</b></a></li>' +
                        '<li class="divider"></li>' +
                        '<li><a href="training.html#events-tab">Events</a></li>' +
                        '<li><a href="training.html#courses-tab">Courses</a></li>' +
                        '<li><a href="training.html#tutorials-tab">Tutorials</a></li>' + 
                        '<li class="divider"></li>' +
                        '<li><a href="testimonials.html">Testimonials</a></li>' +
                    '</ul>' +
                '</li>' +
                '<li class="dropdown">' +
                    '<a href="#" class="dropdown-toggle" data-toggle="dropdown">Collaboration <b class="caret"></b></a>' +
                    '<ul class="dropdown-menu">' +
                        '<li><a href="outreach.html"><b>Collaboration</b></a></li>' +
                        '<li class="divider"></li>' +
                        '<li><a href="gsoc.html">Google Summer of Code</a></li>' +
                        '<li><a href="gsoc.html">NRNB Academy</a></li>' +
                        '<li><a href="competitions.html">NRNB Competitions</a></li>' +
                        '<li><a href="collaboration-report.html">Ongoing Collaborations</a></li>' +
			'<li><a href="collab-wall.html">Wall of Collaborators</a></li>' +
                        '<li><a href="testimonials.html#collab-tab">Testimonials</a></li>' +
                        '<li class="divider"></li>' +
                        '<li><a href="collaboration-tracking.html">Collaboration Request Form</a></li>' +
                    '</ul>' +
                '</li>' +
            '</ul>' +
        '</div>' +
    '</div>' +
    '</div>';

var footerText =
    '<div class="belt black">' +
        '<div class="container">' +
                '<p>' +
                'The National Resource for Network Biology is an NIH Biomedical ' +
                'Technology Research Center (BTRC) supported by the National Institute ' +
                'of General Medical Sciences (NIGMS) under grant P41 GM103504.' +
                '</p>' +
                '<p>' +
                '&copy; NRNB, 2019 <i class="icon-envelope icon-white"></i> ' +
                '<a href="mailto:apico@gladstone.ucsf.edu">email us</a>' +
                '</p>' +
        '</div>' +
    '</div>';

// Insert Nav Bar right after <body> tag.
$('body').prepend(navbarText);

// Insert footer at the end of document.
$('footer').append(footerText);


// For switching tab
$(function () {
    function selectTab(target) {
        console.log('EnterNew: ' + target);
        var activeTab = $('[href=' + target + ']');
        activeTab && activeTab.tab('show');
        window.scrollTo(0, 0);
    }

    $(document).ready(selectTab(location.hash));

    $('#tools-menu').click(function(ev) {
        var locHash = ev.target.href.split("#");
        selectTab('#' + locHash[1]);
    });
    $('#projects-menu').click(function(ev) {
        var locHash = ev.target.href.split("#");
        selectTab('#' + locHash[1]);
    });
    $('#training-menu').click(function(ev) {
        var locHash = ev.target.href.split("#");
        selectTab('#' + locHash[1]);
    });
});
