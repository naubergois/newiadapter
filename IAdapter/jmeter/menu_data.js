fixMozillaZIndex=true; //Fixes Z-Index problem  with Mozilla browsers but causes odd scrolling problem, toggle to see if it helps
_menuCloseDelay=500;
_menuOpenDelay=150;
_subOffsetTop=2;
_subOffsetLeft=-2;


forgetClickValue="true";


with(menuStyle=new mm_style()){
bgimage="http://img.milonic.com/tab_on_cream.gif";
fontfamily="Verdana, Tahoma, Arial";
fontsize="65%";
fontstyle="normal";
fontweight="bold";
itemheight=26;
itemwidth=79;
offcolor="#000000";
oncolor="#000000";
openonclick=1;
subimagepadding=2;
clickbgimage="http://img.milonic.com/tab_on_blue.gif";
}

with(submenuStyle=new mm_style()){
styleid=1;
align="center";
bgimage="http://img.milonic.com/tab_subback.gif";
fontfamily="Verdana, Tahoma, Arial";
fontsize="65%";
fontstyle="normal";
fontweight="bold";
itemheight=29;
offbgcolor="#006699";
offcolor="#ffffff";
oncolor="#ffffff";
ondecoration="underline";
openonclick=1;
padding=6;
separatorimage="http://img.milonic.com/tab_subback_sep.gif";
separatorsize=3;
}

with(milonic=new menuname("Main Menu")){
alwaysvisible=1;
menuwidth=400;
openstyle="tab";
orientation="horizontal";
screenposition="center";
style=menuStyle;
aI("align=center;keepalive=1;showmenu=Milonic;text=MILONIC;");
aI("align=center;keepalive=1;showmenu=Partners;text=PARTNERS;");
aI("align=center;keepalive=1;showmenu=Links;text=LINKS;");
aI("align=center;keepalive=1;showmenu=MyMilonic;text=MYMILONIC;");
aI("align=center;itemwidth=80;keepalive=1;text=SEARCH;");
}

with(milonic=new menuname("Milonic")){
menualign="center";
menuwidth="50%";
orientation="horizontal";
screenposition="center";
style=submenuStyle;
aI("text=MILONIC;url=menu.htm;");
aI("text=CONTACT US;url=menu.htm;");
aI("text=NEWSLETTER;url=menu.htm;");
aI("text=FAQ;url=menu.htm;");
aI("text=DISCUSSION FORUM;url=menu.htm;");
aI("text=LICENSE AGREEMENT;url=menu.htm;");
aI("separatorsize=4;");
}

with(milonic=new menuname("Partners")){
menualign="center";
menuwidth="50%";
orientation="horizontal";
screenposition="center";
style=submenuStyle;
aI("text=PARTNERS;url=menu.htm?test=1;");
aI("text=SMS 2 EMAIL;url=menu.htm;");
aI("text=WEBSMITH;url=menu.htm;");
aI("separatorsize=4;");
}

with(milonic=new menuname("Links")){
menualign="center";
menuwidth="50%";
orientation="horizontal";
screenposition="center";
style=submenuStyle;
aI("text=LINKS;url=menu.htm;");
aI("text=MYSQL;url=menu.htm;");
aI("text=PHP;url=menu.htm;");
aI("text=PHPBB WEB FORUM;url=menu.htm;");
aI("separatorsize=4;");
}

with(milonic=new menuname("MyMilonic")){
menualign="center";
menuwidth="50%";
orientation="horizontal";
screenposition="center";
style=submenuStyle;
aI("text=MYMILONIC;url=menu.htm;");
aI("text=LICENSES;url=menu.htm;");
aI("text=INVOICES;url=menu.htm;");
aI("text=MAKE SUPPORT;url=menu.htm;");
aI("text=VIEW SUPPORT;url=menu.htm;");
aI("text=YOUR DETAILS;url=menu.htm;");
aI("separatorsize=4;");
}

drawMenus();

