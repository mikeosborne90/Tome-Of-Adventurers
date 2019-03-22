package com.at.gmail.tomeofadventurers.Classes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CharacterDBAccess {

    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static CharacterDBAccess instance;

    //General database functions -----------------------------------------------------------------
    private CharacterDBAccess(Context context) {
        this.openHelper = new DatabaseHelper(context);
    }

    public static CharacterDBAccess getInstance(Context context) {
        if (instance == null) {
            instance = new CharacterDBAccess(context);
        }
        return instance;
    }

    public void open() {
        this.database = openHelper.getWritableDatabase();
    }

    public void close() {
        if (database != null) {
            this.database.close();
        }
    }

    public List<String> getCharacterNames() {
        List<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT Name FROM characters", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public String getIDFromCharacters(String charName){
        String query = "SELECT id FROM characters WHERE Name = '" + charName + "'";
        Cursor data = database.rawQuery(query, null);

        String charID = "_";

        while (data.moveToNext()) {
            charID = data.getString(0);
        }

        data.close();

        return charID;
    }

    public void chooseCharacter(String charID)
    {
        String query1 = "UPDATE characters SET Selected = 0";
        database.execSQL(query1);

        String query2 = "UPDATE characters SET Selected = 1 WHERE id = '"+ charID +"'";
        database.execSQL(query2);
    }

    public void clearSelectedCharacters()
    {
        String query = "UPDATE characters SET Selected = 0";
        database.execSQL(query);
    }

    public String findSelectedCharacter()
    {
        String query = "SELECT id FROM characters WHERE Selected = 1";
        Cursor data = database.rawQuery(query, null);

        String charID = "_";

        while (data.moveToNext()) {
            charID = data.getString(0);
        }

        data.close();

        return charID;
    }

//Below functions mainly used to fill character sheet fragment
    public String loadCharacterName()
    {
        String query = "SELECT Name FROM characters WHERE Selected = 1";
        Cursor data = database.rawQuery(query, null);

        String name = "_";

        while (data.moveToNext()) {
            name = data.getString(0);
        }

        data.close();

        return name;
    }

    public String loadCharacterClass()
    {
        String query = "SELECT Class FROM characters WHERE Selected = 1";
        Cursor data = database.rawQuery(query, null);

        String dclass = "_";

        while (data.moveToNext()) {
            dclass = data.getString(0);
        }

        data.close();

        return dclass;
    }

    public String loadCharacterRace()
    {
        String query = "SELECT Race FROM characters WHERE Selected = 1";
        Cursor data = database.rawQuery(query, null);

        String race = "_";

        while (data.moveToNext()) {
            race = data.getString(0);
        }

        data.close();

        return race;
    }

    public String loadCharacterHitDice()
    {
        String query = "SELECT HitDice FROM characters WHERE Selected = 1";
        Cursor data = database.rawQuery(query, null);

        String hitDice = "_";

        while (data.moveToNext()) {
            hitDice = data.getString(0);
        }

        data.close();

        return hitDice;
    }

    public String loadCharacterArmorClass()
    {
        String query = "SELECT AC FROM characters WHERE Selected = 1";
        Cursor data = database.rawQuery(query, null);

        String armorClass = "_";

        while (data.moveToNext()) {
            armorClass = data.getString(0);
        }

        data.close();

        return armorClass;
    }

    public String loadCharacterSpeed()
    {
        String query = "SELECT Speed FROM characters WHERE Selected = 1";
        Cursor data = database.rawQuery(query, null);

        String speed = "_";

        while (data.moveToNext()) {
            speed = data.getString(0);
        }

        data.close();

        return speed;
    }

    public int[] loadAbilityScores()
    {
        int[] abilityScores = {0,0,0,0,0,0};

        String query = "SELECT Str, Dex, Con, Int, Wis, Cha FROM characters WHERE Selected = 1";
        Cursor data = database.rawQuery(query, null);

        String str = "_";
        String dex = "_";
        String con = "_";
        String intell = "_";
        String wis = "_";
        String cha = "_";

        while (data.moveToNext()) {
            str = data.getString(0);
            dex = data.getString(1);
            con = data.getString(2);
            intell = data.getString(3);
            wis = data.getString(4);
            cha = data.getString(5);
        }

        data.close();

        if(str != null)
        {
            abilityScores[0] = Integer.parseInt(str);
            abilityScores[1] = Integer.parseInt(dex);
            abilityScores[2] = Integer.parseInt(con);
            abilityScores[3] = Integer.parseInt(intell);
            abilityScores[4] = Integer.parseInt(wis);
            abilityScores[5] = Integer.parseInt(cha);
        }

        return abilityScores;
    }

    public int[] loadAbilityScoresModifiers()
    {
        int[] abilityScoresModifiers = {0,0,0,0,0,0};

        String query = "SELECT StrMod, DexMod, ConMod, IntMod, WisMod, ChaMod FROM characters WHERE Selected = 1";
        Cursor data = database.rawQuery(query, null);

        //Modifiers for ability scores

        String str = "_";
        String dex = "_";
        String con = "_";
        String intell = "_";
        String wis = "_";
        String cha = "_";

        while (data.moveToNext()) {
            str = data.getString(0);
            dex = data.getString(1);
            con = data.getString(2);
            intell = data.getString(3);
            wis = data.getString(4);
            cha = data.getString(5);
        }

        data.close();

        if(str != null)
        {
            abilityScoresModifiers[0] = Integer.parseInt(str);
            abilityScoresModifiers[1] = Integer.parseInt(dex);
            abilityScoresModifiers[2] = Integer.parseInt(con);
            abilityScoresModifiers[3] = Integer.parseInt(intell);
            abilityScoresModifiers[4] = Integer.parseInt(wis);
            abilityScoresModifiers[5] = Integer.parseInt(cha);
        }

        return abilityScoresModifiers;
    }

    public int[] loadSkillModifiers()
    {
        int[] skillModifiers = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}; //18 total

        String query = "SELECT Acrobatics, AnimalHandling, Arcana, Athletics, Deception, History, Insight," +
                " Intimidation, Investigation, Medicine, Nature, Perception, Performance, " +
                "Persuasion, Religion, SleightOfHand, Stealth, Survival FROM characters WHERE Selected = 1";
        Cursor data = database.rawQuery(query, null);

        //Modifiers for skills

        String ACR = "_", AH = "_", ARC = "_", ATH = "_", DEC = "_", HIST = "_", INS = "_";
        String INTI = "_", INV = "_", MED = "_", NAT = "_", PERC = "_", PERF = "_";
        String PERS = "_", REL = "_", SOH = "_", STLH = "_", SURV = "_";

        while (data.moveToNext()) {
            ACR = data.getString(0);
            AH = data.getString(1);
            ARC = data.getString(2);
            ATH = data.getString(3);
            DEC = data.getString(4);
            HIST = data.getString(5);
            INS = data.getString(6);
            INTI = data.getString(7);
            INV = data.getString(8);
            MED = data.getString(9);
            NAT = data.getString(10);
            PERC = data.getString(11);
            PERF = data.getString(12);
            PERS = data.getString(13);
            REL = data.getString(14);
            SOH = data.getString(15);
            STLH = data.getString(16);
            SURV = data.getString(17);
        }

        data.close();

        if(ACR != null)
        {
            skillModifiers[0] = Integer.parseInt(ACR);
            skillModifiers[1] = Integer.parseInt(AH);
            skillModifiers[2] = Integer.parseInt(ARC);
            skillModifiers[3] = Integer.parseInt(ATH);
            skillModifiers[4] = Integer.parseInt(DEC);
            skillModifiers[5] = Integer.parseInt(HIST);
            skillModifiers[6] = Integer.parseInt(INS);
            skillModifiers[7] = Integer.parseInt(INTI);
            skillModifiers[8] = Integer.parseInt(INV);
            skillModifiers[9] = Integer.parseInt(MED);
            skillModifiers[10] = Integer.parseInt(NAT);
            skillModifiers[11] = Integer.parseInt(PERC);
            skillModifiers[12] = Integer.parseInt(PERF);
            skillModifiers[13] = Integer.parseInt(PERS);
            skillModifiers[14] = Integer.parseInt(REL);
            skillModifiers[15] = Integer.parseInt(SOH);
            skillModifiers[16] = Integer.parseInt(STLH);
            skillModifiers[17] = Integer.parseInt(SURV);
        }


        return skillModifiers;
    }

    public int loadCharacterMaxHP()
    {
        String query = "SELECT MaxHitPoints FROM characters WHERE Selected = 1";
        Cursor data = database.rawQuery(query, null);

        String maxHP = "_";

        while (data.moveToNext()) {
            maxHP = data.getString(0);
        }

        data.close();

        return Integer.parseInt(maxHP);
    }

    public int loadCharacterCurrentHP()
    {
        String query = "SELECT CurrentHitPoints FROM characters WHERE Selected = 1";
        Cursor data = database.rawQuery(query, null);

        String hp = "_";

        while (data.moveToNext()) {
            hp = data.getString(0);
        }

        data.close();

        return Integer.parseInt(hp);
    }

    public int loadCharacterProfBonus()
    {
        String query = "SELECT ProfBonus FROM characters WHERE Selected = 1";
        Cursor data = database.rawQuery(query, null);

        String pB = "_";
        int profBonusAmount = 0; //initializer

        while (data.moveToNext()) {
            pB = data.getString(0);
        }

        data.close();

        if(pB != null)
        {
            profBonusAmount = Integer.parseInt(pB);
        }

        return profBonusAmount;
    }

    public String loadCharacterPassivePerception()
    {
        String query = "SELECT PassivePerception FROM characters WHERE Selected = 1";
        Cursor data = database.rawQuery(query, null);

        String pP = "_";

        while (data.moveToNext()) {
            pP = data.getString(0);
        }

        data.close();

        return pP;
    }

    public void saveCurrentHP(int currentHP)
    {
        String query = "UPDATE characters SET CurrentHitPoints = '"+ currentHP +"' WHERE Selected = 1";
        database.execSQL(query);
    }

    public boolean[] loadSkillProficiencies()
    {
        boolean[] skillProficiencies = {false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false}; //18 total

        String query = "SELECT AcrobaticsProf, AnimalHandlingProf, ArcanaProf, AthleticsProf, DeceptionProf, HistoryProf, InsightProf," +
                " IntimidationProf, InvestigationProf, MedicineProf, NatureProf, PerceptionProf, PerformanceProf, " +
                "PersuasionProf, ReligionProf, SleightOfHandProf, StealthProf, SurvivalProf FROM characters WHERE Selected = 1";
        Cursor data = database.rawQuery(query, null);

        while (data.moveToNext())
        {
            for(int i = 0; i< 18; i++)
            {
                if(data.getString(i).equals(Integer.toString(1)))
                {
                    skillProficiencies[i] = true;
                }
            }
        }

        data.close();

        return skillProficiencies;
    }

    public boolean saveCharacter(Character aCharacter)
    {
        Random rand = new Random();

        int rand_int1 = rand.nextInt(1000);
        int rand_int2 = rand.nextInt(27); //used to choose letter

        char[] alphabet = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};

        char rand_char1 = alphabet[rand_int2];

        String charID = Integer.toString(rand_int1)+rand_char1;

        String name, race, dclass, hitdice, armorclass, hp, speed;
        int[] abilityscores;
        int[] abilityscoremod;
        int[] skillmodifiers;
        boolean[] skillProficiencies;

        name = aCharacter.getName();
        race = aCharacter.getRaceName();
        dclass = aCharacter.getClassName();
        hitdice = aCharacter.getMyHitDice();
        armorclass = Integer.toString(aCharacter.getArmorClass());
        hp = Integer.toString(aCharacter.getCurrentHitPoints());
        speed = Integer.toString(aCharacter.getSpeed());
        abilityscores = aCharacter.getAbilityScores();
        abilityscoremod = aCharacter.getAllAbilityScoreModifiers();
        skillmodifiers = aCharacter.getAllSkillModifiers();
        skillProficiencies = aCharacter.getAllSkillProficiencies();


        int[] dbSkillProficiencies = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};

        for(int i = 0; i < 18; i++) //convert booleans into 1's and zeros for DB
        {
            if(skillProficiencies[i] == true)
            {
                dbSkillProficiencies[i] = 1;
            }
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put("Selected", 1);  //need to have id for new items later!!
        contentValues.put("id", charID);
        contentValues.put("Name", name);
        contentValues.put("Race", race);
        contentValues.put("Class", dclass);
        contentValues.put("HitDice", hitdice);
        contentValues.put("AC", armorclass);
        contentValues.put("Speed", speed);
        contentValues.put("CurrentHitPoints", hp);
        contentValues.put("MaxHitPoints", hp);

        contentValues.put("Str", abilityscores[0]);
        contentValues.put("Dex", abilityscores[1]);
        contentValues.put("Con", abilityscores[2]);
        contentValues.put("Int", abilityscores[3]);
        contentValues.put("Wis", abilityscores[4]);
        contentValues.put("Cha", abilityscores[5]);

        contentValues.put("StrMod", abilityscoremod[0]);
        contentValues.put("DexMod", abilityscoremod[1]);
        contentValues.put("ConMod", abilityscoremod[2]);
        contentValues.put("IntMod", abilityscoremod[3]);
        contentValues.put("WisMod", abilityscoremod[4]);
        contentValues.put("ChaMod", abilityscoremod[5]);

        contentValues.put("Acrobatics", skillmodifiers[0]);
        contentValues.put("AnimalHandling", skillmodifiers[1]);
        contentValues.put("Arcana", skillmodifiers[2]);
        contentValues.put("Athletics", skillmodifiers[3]);
        contentValues.put("Deception", skillmodifiers[4]);
        contentValues.put("History", skillmodifiers[5]);
        contentValues.put("Insight", skillmodifiers[6]);
        contentValues.put("Intimidation", skillmodifiers[7]);
        contentValues.put("Investigation", skillmodifiers[8]);
        contentValues.put("Medicine", skillmodifiers[9]);
        contentValues.put("Nature", skillmodifiers[10]);
        contentValues.put("Perception", skillmodifiers[11]);
        contentValues.put("Performance", skillmodifiers[12]);
        contentValues.put("Persuasion", skillmodifiers[13]);
        contentValues.put("Religion", skillmodifiers[14]);
        contentValues.put("SleightOfHand", skillmodifiers[15]);
        contentValues.put("Stealth", skillmodifiers[16]);
        contentValues.put("Survival", skillmodifiers[17]);

        contentValues.put("AcrobaticsProf", dbSkillProficiencies[0]);
        contentValues.put("AnimalHandlingProf", dbSkillProficiencies[1]);
        contentValues.put("ArcanaProf", dbSkillProficiencies[2]);
        contentValues.put("AthleticsProf", dbSkillProficiencies[3]);
        contentValues.put("DeceptionProf", dbSkillProficiencies[4]);
        contentValues.put("HistoryProf", dbSkillProficiencies[5]);
        contentValues.put("InsightProf", dbSkillProficiencies[6]);
        contentValues.put("IntimidationProf", dbSkillProficiencies[7]);
        contentValues.put("InvestigationProf", dbSkillProficiencies[8]);
        contentValues.put("MedicineProf", dbSkillProficiencies[9]);
        contentValues.put("NatureProf", dbSkillProficiencies[10]);
        contentValues.put("PerceptionProf", dbSkillProficiencies[11]);
        contentValues.put("PerformanceProf", dbSkillProficiencies[12]);
        contentValues.put("PersuasionProf", dbSkillProficiencies[13]);
        contentValues.put("ReligionProf", dbSkillProficiencies[14]);
        contentValues.put("SleightOfHandProf", dbSkillProficiencies[15]);
        contentValues.put("StealthProf", dbSkillProficiencies[16]);
        contentValues.put("SurvivalProf", dbSkillProficiencies[17]);

        long result = database.insert("characters", null, contentValues);

        //if data is inserted incorrectly it will return -1
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }
}