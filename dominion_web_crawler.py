import time
from bs4 import BeautifulSoup
import numpy as np
from urllib.request import urlopen
def get_card_info(url):
    html = urlopen(url)
    soup = BeautifulSoup(html, 'html.parser')
    tables = soup.body.find_all("table")
    rows = soup.table.find_all("tr")
    num_actions = 0
    num_value = 0
    num_cards = 0
    num_cost = 0
    num_victory = 0
    num_buy = 0
    can_trash = False
    card_type = ""
    card_text = ""
    for row in rows:
        #print(row)
        if row.th is not None and 'Cost' in row.th.text:
            num_cost = row.td.span.img['alt'].replace("$", "")
        if row.th is not None and "Type" in row.th.text:
            card_type = row.td.text.replace('\n','')
            print(card_type)
            if 'Event' in card_type or 'Landmark' in card_type or 'Hex' in card_type or 'Boon' in card_type:
                return None
            card_text = True
        if row.th is not None and "Card text" in row.th.text:
            c_next_row = row.next_sibling
            #print(card_type)
            if ("Victory" in card_type or "Curse" in card_type) and 'Castle' not in card_type:
                num_victory = c_next_row.td.i.span.text
            if "Action" in card_type:
                if c_next_row.td.i.br is not None:
                    for br in c_next_row.td.i.select('br'):
                        br.replace_with('|')
                for i in c_next_row.td.i.text.split('|'):
                    #print(i)
                    if '+' in i:
                        words = i.split(' ')
                        #print(i)
                        #print(words)
                        if "+" in words[0]:
                            text = words[0].replace("+",'')
                            #print(words[0])
                            #print(text)
                            if text == '':
                                value = int(c_next_row.td.i.span.img['alt'].replace('$',''))
                                #print("COIN VALUE ON CARD -- {}".format(value))
                            else:
                                value = int(text)
                        #print(len(words))
                        if len(words) == 1:
                            num_value += value
                        elif 'Action' in words[1]:
                            num_actions+= value
                        elif 'Card' in words[1]:
                            num_cards+= value
                        elif 'Buy' in words[1]:
                            num_buy+= value
                    if 'trash' in i or 'Trash' in i:
                        # fix trashing -- ie like chapel
                        
                        can_trash = True


                card_text = c_next_row.td.i.get_text()
            if "Treasure" in card_type:
                num_value += int(c_next_row.td.i.span.img['alt'].replace("$", ""))
    rtn_card_info = "type:{}|A:{}|B:{}|C:{}|T:{}|V:{}|VP:{}".format(card_type, num_actions,num_buy, num_cards, can_trash, num_value, num_victory)
    return rtn_card_info 

            #if "+" in tr.string:
            #    if "Action" in tr.td.i.string:
            #    if "Card" in tr.string:
            #    if "Buy" in tr.string:
    
    '''
    for div in divs:
        if div.has_attr("id") and "mw-content-text" == div['id']:
            tables = div.find_all("table")
            table = div.table
            print(div['id'])
            tbodys = table.find_all("tbody")
            for tbody in tbodys:
                print("tables...")

                trs = tbody.find_all("tr")
                num_actions = 0
                num_value = 0
                num_cards = 0
                print("TABLES")
                for tr in trs:
                    if "Type" in tr.th.string and "Action" not in tr.td.string:
                        num_actions = -1
                    print(tr.string)
                        #if "+" in tr.string:
                        #    if "Action" in tr.td.i.string:
                        #    if "Card" in tr.string:
                        #    if "Buy" in tr.string:
    '''
                        
    return ""

urls = [
'http://wiki.dominionstrategy.com/index.php/Dominion_(Base_Set)',
'http://wiki.dominionstrategy.com/index.php/Intrigue',
'http://wiki.dominionstrategy.com/index.php/Empires',
]
add_to_file = []

for url in urls:
    print(url)
    html = urlopen(url) 
    soup = BeautifulSoup(html, 'html.parser')
    divs = soup.body.find_all("div")
    for div in divs:
        if div.has_attr('id') and div['id'] == 'content':
            bodyDivs = div.find_all("div")
            for divTwo in bodyDivs:
                if divTwo.has_attr('id') and divTwo['id'] == 'bodyContent':
                    uls = divTwo.find_all("ul")
                    for ul in uls:
                        lis = ul.find_all("li")
                        for li in lis:
                            spans = li.find_all("span")
                            value = -1
                            for span in spans:
                                next_url = ""
                                name = ""
                                if span.has_attr("class") and "coin-icon" in span["class"]:
                                    value = span.img["alt"].replace('$', '')
                                    print("COIN ICON -- value: " + value)
                                elif value == -1:
                                    break
                                elif span.has_attr("class") and "card-popup" in span['class']:
                                    next_url = "http://wiki.dominionstrategy.com" + span.a['href']
                                    #print(next_url)
                                    name = span.a["title"]
                                    s = get_card_info(next_url)
                                    if s is None:
                                        continue
                                    send_to_file = "name:{}|cost:{}|{}\n".format(name, value, s)
                                    add_to_file.append(send_to_file)
    print(send_to_file);
f = open("dominion_cards.txt", "w")
for s in add_to_file:
    f.write(s)
f.close()
# not defined??
