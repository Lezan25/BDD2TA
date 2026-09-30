package dk.sdu.parser.antlr.internal;

import org.eclipse.xtext.*;
import org.eclipse.xtext.parser.*;
import org.eclipse.xtext.parser.impl.*;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.xtext.parser.antlr.AbstractInternalAntlrParser;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import org.eclipse.xtext.parser.antlr.XtextTokenStream.HiddenTokens;
import org.eclipse.xtext.parser.antlr.AntlrDatatypeRuleToken;
import dk.sdu.services.BddGrammarAccess;



import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class InternalBddParser extends AbstractInternalAntlrParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_ID", "RULE_INT", "RULE_STRING", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "RULE_ANY_OTHER", "'model'", "'entity'", "'{'", "'states:'", "'properties:'", "'}'", "'='", "'scenario:'", "'given'", "'and'", "'then'", "'when'", "'whenever'", "'have'", "'passed'", "'finishes'", "'is'", "'not'", "'in'", "'between'", "','", "'by'", "'s'", "'requirement:'", "'the'", "'system'", "'running'", "'check'", "'within'", "'can'", "'should'", "'eventually'", "'always'", "'be'", "'never'", "'maximum'", "'of'", "'minimum'", "'milliseconds'", "'seconds'", "'minutes'", "'hours'", "'increased'", "'decreased'", "'is_greater_or_equal_to'", "'is_less_or_equal_to'", "'is_greater_than'", "'is_less_than'", "'is_not'"
    };
    public static final int T__50=50;
    public static final int T__19=19;
    public static final int T__15=15;
    public static final int T__59=59;
    public static final int T__16=16;
    public static final int T__17=17;
    public static final int T__18=18;
    public static final int T__11=11;
    public static final int T__55=55;
    public static final int T__12=12;
    public static final int T__56=56;
    public static final int T__13=13;
    public static final int T__57=57;
    public static final int T__14=14;
    public static final int T__58=58;
    public static final int T__51=51;
    public static final int T__52=52;
    public static final int T__53=53;
    public static final int T__54=54;
    public static final int RULE_ID=4;
    public static final int T__26=26;
    public static final int T__27=27;
    public static final int T__28=28;
    public static final int RULE_INT=5;
    public static final int T__29=29;
    public static final int T__22=22;
    public static final int RULE_ML_COMMENT=7;
    public static final int T__23=23;
    public static final int T__24=24;
    public static final int T__25=25;
    public static final int T__20=20;
    public static final int T__21=21;
    public static final int RULE_STRING=6;
    public static final int RULE_SL_COMMENT=8;
    public static final int T__37=37;
    public static final int T__38=38;
    public static final int T__39=39;
    public static final int T__33=33;
    public static final int T__34=34;
    public static final int T__35=35;
    public static final int T__36=36;
    public static final int EOF=-1;
    public static final int T__30=30;
    public static final int T__31=31;
    public static final int T__32=32;
    public static final int RULE_WS=9;
    public static final int RULE_ANY_OTHER=10;
    public static final int T__48=48;
    public static final int T__49=49;
    public static final int T__44=44;
    public static final int T__45=45;
    public static final int T__46=46;
    public static final int T__47=47;
    public static final int T__40=40;
    public static final int T__41=41;
    public static final int T__42=42;
    public static final int T__43=43;

    // delegates
    // delegators


        public InternalBddParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public InternalBddParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return InternalBddParser.tokenNames; }
    public String getGrammarFileName() { return "InternalBdd.g"; }



     	private BddGrammarAccess grammarAccess;

        public InternalBddParser(TokenStream input, BddGrammarAccess grammarAccess) {
            this(input);
            this.grammarAccess = grammarAccess;
            registerRules(grammarAccess.getGrammar());
        }

        @Override
        protected String getFirstRuleName() {
        	return "Model";
       	}

       	@Override
       	protected BddGrammarAccess getGrammarAccess() {
       		return grammarAccess;
       	}




    // $ANTLR start "entryRuleModel"
    // InternalBdd.g:65:1: entryRuleModel returns [EObject current=null] : iv_ruleModel= ruleModel EOF ;
    public final EObject entryRuleModel() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleModel = null;


        try {
            // InternalBdd.g:65:46: (iv_ruleModel= ruleModel EOF )
            // InternalBdd.g:66:2: iv_ruleModel= ruleModel EOF
            {
             newCompositeNode(grammarAccess.getModelRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleModel=ruleModel();

            state._fsp--;

             current =iv_ruleModel; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleModel"


    // $ANTLR start "ruleModel"
    // InternalBdd.g:72:1: ruleModel returns [EObject current=null] : (otherlv_0= 'model' ( (lv_name_1_0= RULE_ID ) ) ( (lv_entities_2_0= ruleEntity ) )+ ( (lv_scenarios_3_0= ruleScenario ) )+ ( (lv_requirements_4_0= ruleRequirement ) )* ) ;
    public final EObject ruleModel() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;
        EObject lv_entities_2_0 = null;

        EObject lv_scenarios_3_0 = null;

        EObject lv_requirements_4_0 = null;



        	enterRule();

        try {
            // InternalBdd.g:78:2: ( (otherlv_0= 'model' ( (lv_name_1_0= RULE_ID ) ) ( (lv_entities_2_0= ruleEntity ) )+ ( (lv_scenarios_3_0= ruleScenario ) )+ ( (lv_requirements_4_0= ruleRequirement ) )* ) )
            // InternalBdd.g:79:2: (otherlv_0= 'model' ( (lv_name_1_0= RULE_ID ) ) ( (lv_entities_2_0= ruleEntity ) )+ ( (lv_scenarios_3_0= ruleScenario ) )+ ( (lv_requirements_4_0= ruleRequirement ) )* )
            {
            // InternalBdd.g:79:2: (otherlv_0= 'model' ( (lv_name_1_0= RULE_ID ) ) ( (lv_entities_2_0= ruleEntity ) )+ ( (lv_scenarios_3_0= ruleScenario ) )+ ( (lv_requirements_4_0= ruleRequirement ) )* )
            // InternalBdd.g:80:3: otherlv_0= 'model' ( (lv_name_1_0= RULE_ID ) ) ( (lv_entities_2_0= ruleEntity ) )+ ( (lv_scenarios_3_0= ruleScenario ) )+ ( (lv_requirements_4_0= ruleRequirement ) )*
            {
            otherlv_0=(Token)match(input,11,FOLLOW_3); 

            			newLeafNode(otherlv_0, grammarAccess.getModelAccess().getModelKeyword_0());
            		
            // InternalBdd.g:84:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalBdd.g:85:4: (lv_name_1_0= RULE_ID )
            {
            // InternalBdd.g:85:4: (lv_name_1_0= RULE_ID )
            // InternalBdd.g:86:5: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_4); 

            					newLeafNode(lv_name_1_0, grammarAccess.getModelAccess().getNameIDTerminalRuleCall_1_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getModelRule());
            					}
            					setWithLastConsumed(
            						current,
            						"name",
            						lv_name_1_0,
            						"org.eclipse.xtext.common.Terminals.ID");
            				

            }


            }

            // InternalBdd.g:102:3: ( (lv_entities_2_0= ruleEntity ) )+
            int cnt1=0;
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==12) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // InternalBdd.g:103:4: (lv_entities_2_0= ruleEntity )
            	    {
            	    // InternalBdd.g:103:4: (lv_entities_2_0= ruleEntity )
            	    // InternalBdd.g:104:5: lv_entities_2_0= ruleEntity
            	    {

            	    					newCompositeNode(grammarAccess.getModelAccess().getEntitiesEntityParserRuleCall_2_0());
            	    				
            	    pushFollow(FOLLOW_5);
            	    lv_entities_2_0=ruleEntity();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getModelRule());
            	    					}
            	    					add(
            	    						current,
            	    						"entities",
            	    						lv_entities_2_0,
            	    						"dk.sdu.Bdd.Entity");
            	    					afterParserOrEnumRuleCall();
            	    				

            	    }


            	    }
            	    break;

            	default :
            	    if ( cnt1 >= 1 ) break loop1;
                        EarlyExitException eee =
                            new EarlyExitException(1, input);
                        throw eee;
                }
                cnt1++;
            } while (true);

            // InternalBdd.g:121:3: ( (lv_scenarios_3_0= ruleScenario ) )+
            int cnt2=0;
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0==18) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // InternalBdd.g:122:4: (lv_scenarios_3_0= ruleScenario )
            	    {
            	    // InternalBdd.g:122:4: (lv_scenarios_3_0= ruleScenario )
            	    // InternalBdd.g:123:5: lv_scenarios_3_0= ruleScenario
            	    {

            	    					newCompositeNode(grammarAccess.getModelAccess().getScenariosScenarioParserRuleCall_3_0());
            	    				
            	    pushFollow(FOLLOW_6);
            	    lv_scenarios_3_0=ruleScenario();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getModelRule());
            	    					}
            	    					add(
            	    						current,
            	    						"scenarios",
            	    						lv_scenarios_3_0,
            	    						"dk.sdu.Bdd.Scenario");
            	    					afterParserOrEnumRuleCall();
            	    				

            	    }


            	    }
            	    break;

            	default :
            	    if ( cnt2 >= 1 ) break loop2;
                        EarlyExitException eee =
                            new EarlyExitException(2, input);
                        throw eee;
                }
                cnt2++;
            } while (true);

            // InternalBdd.g:140:3: ( (lv_requirements_4_0= ruleRequirement ) )*
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( (LA3_0==34) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // InternalBdd.g:141:4: (lv_requirements_4_0= ruleRequirement )
            	    {
            	    // InternalBdd.g:141:4: (lv_requirements_4_0= ruleRequirement )
            	    // InternalBdd.g:142:5: lv_requirements_4_0= ruleRequirement
            	    {

            	    					newCompositeNode(grammarAccess.getModelAccess().getRequirementsRequirementParserRuleCall_4_0());
            	    				
            	    pushFollow(FOLLOW_7);
            	    lv_requirements_4_0=ruleRequirement();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getModelRule());
            	    					}
            	    					add(
            	    						current,
            	    						"requirements",
            	    						lv_requirements_4_0,
            	    						"dk.sdu.Bdd.Requirement");
            	    					afterParserOrEnumRuleCall();
            	    				

            	    }


            	    }
            	    break;

            	default :
            	    break loop3;
                }
            } while (true);


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleModel"


    // $ANTLR start "entryRuleEntity"
    // InternalBdd.g:163:1: entryRuleEntity returns [EObject current=null] : iv_ruleEntity= ruleEntity EOF ;
    public final EObject entryRuleEntity() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleEntity = null;


        try {
            // InternalBdd.g:163:47: (iv_ruleEntity= ruleEntity EOF )
            // InternalBdd.g:164:2: iv_ruleEntity= ruleEntity EOF
            {
             newCompositeNode(grammarAccess.getEntityRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleEntity=ruleEntity();

            state._fsp--;

             current =iv_ruleEntity; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleEntity"


    // $ANTLR start "ruleEntity"
    // InternalBdd.g:170:1: ruleEntity returns [EObject current=null] : (otherlv_0= 'entity' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'states:' ( (lv_states_4_0= ruleState ) )+ (otherlv_5= 'properties:' ( (lv_properties_6_0= ruleProperty ) )* )? otherlv_7= '}' ) ;
    public final EObject ruleEntity() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        Token otherlv_7=null;
        EObject lv_states_4_0 = null;

        EObject lv_properties_6_0 = null;



        	enterRule();

        try {
            // InternalBdd.g:176:2: ( (otherlv_0= 'entity' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'states:' ( (lv_states_4_0= ruleState ) )+ (otherlv_5= 'properties:' ( (lv_properties_6_0= ruleProperty ) )* )? otherlv_7= '}' ) )
            // InternalBdd.g:177:2: (otherlv_0= 'entity' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'states:' ( (lv_states_4_0= ruleState ) )+ (otherlv_5= 'properties:' ( (lv_properties_6_0= ruleProperty ) )* )? otherlv_7= '}' )
            {
            // InternalBdd.g:177:2: (otherlv_0= 'entity' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'states:' ( (lv_states_4_0= ruleState ) )+ (otherlv_5= 'properties:' ( (lv_properties_6_0= ruleProperty ) )* )? otherlv_7= '}' )
            // InternalBdd.g:178:3: otherlv_0= 'entity' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'states:' ( (lv_states_4_0= ruleState ) )+ (otherlv_5= 'properties:' ( (lv_properties_6_0= ruleProperty ) )* )? otherlv_7= '}'
            {
            otherlv_0=(Token)match(input,12,FOLLOW_3); 

            			newLeafNode(otherlv_0, grammarAccess.getEntityAccess().getEntityKeyword_0());
            		
            // InternalBdd.g:182:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalBdd.g:183:4: (lv_name_1_0= RULE_ID )
            {
            // InternalBdd.g:183:4: (lv_name_1_0= RULE_ID )
            // InternalBdd.g:184:5: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_8); 

            					newLeafNode(lv_name_1_0, grammarAccess.getEntityAccess().getNameIDTerminalRuleCall_1_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getEntityRule());
            					}
            					setWithLastConsumed(
            						current,
            						"name",
            						lv_name_1_0,
            						"org.eclipse.xtext.common.Terminals.ID");
            				

            }


            }

            otherlv_2=(Token)match(input,13,FOLLOW_9); 

            			newLeafNode(otherlv_2, grammarAccess.getEntityAccess().getLeftCurlyBracketKeyword_2());
            		
            otherlv_3=(Token)match(input,14,FOLLOW_3); 

            			newLeafNode(otherlv_3, grammarAccess.getEntityAccess().getStatesKeyword_3());
            		
            // InternalBdd.g:208:3: ( (lv_states_4_0= ruleState ) )+
            int cnt4=0;
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( (LA4_0==RULE_ID) ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // InternalBdd.g:209:4: (lv_states_4_0= ruleState )
            	    {
            	    // InternalBdd.g:209:4: (lv_states_4_0= ruleState )
            	    // InternalBdd.g:210:5: lv_states_4_0= ruleState
            	    {

            	    					newCompositeNode(grammarAccess.getEntityAccess().getStatesStateParserRuleCall_4_0());
            	    				
            	    pushFollow(FOLLOW_10);
            	    lv_states_4_0=ruleState();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getEntityRule());
            	    					}
            	    					add(
            	    						current,
            	    						"states",
            	    						lv_states_4_0,
            	    						"dk.sdu.Bdd.State");
            	    					afterParserOrEnumRuleCall();
            	    				

            	    }


            	    }
            	    break;

            	default :
            	    if ( cnt4 >= 1 ) break loop4;
                        EarlyExitException eee =
                            new EarlyExitException(4, input);
                        throw eee;
                }
                cnt4++;
            } while (true);

            // InternalBdd.g:227:3: (otherlv_5= 'properties:' ( (lv_properties_6_0= ruleProperty ) )* )?
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==15) ) {
                alt6=1;
            }
            switch (alt6) {
                case 1 :
                    // InternalBdd.g:228:4: otherlv_5= 'properties:' ( (lv_properties_6_0= ruleProperty ) )*
                    {
                    otherlv_5=(Token)match(input,15,FOLLOW_11); 

                    				newLeafNode(otherlv_5, grammarAccess.getEntityAccess().getPropertiesKeyword_5_0());
                    			
                    // InternalBdd.g:232:4: ( (lv_properties_6_0= ruleProperty ) )*
                    loop5:
                    do {
                        int alt5=2;
                        int LA5_0 = input.LA(1);

                        if ( (LA5_0==RULE_ID) ) {
                            alt5=1;
                        }


                        switch (alt5) {
                    	case 1 :
                    	    // InternalBdd.g:233:5: (lv_properties_6_0= ruleProperty )
                    	    {
                    	    // InternalBdd.g:233:5: (lv_properties_6_0= ruleProperty )
                    	    // InternalBdd.g:234:6: lv_properties_6_0= ruleProperty
                    	    {

                    	    						newCompositeNode(grammarAccess.getEntityAccess().getPropertiesPropertyParserRuleCall_5_1_0());
                    	    					
                    	    pushFollow(FOLLOW_11);
                    	    lv_properties_6_0=ruleProperty();

                    	    state._fsp--;


                    	    						if (current==null) {
                    	    							current = createModelElementForParent(grammarAccess.getEntityRule());
                    	    						}
                    	    						add(
                    	    							current,
                    	    							"properties",
                    	    							lv_properties_6_0,
                    	    							"dk.sdu.Bdd.Property");
                    	    						afterParserOrEnumRuleCall();
                    	    					

                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop5;
                        }
                    } while (true);


                    }
                    break;

            }

            otherlv_7=(Token)match(input,16,FOLLOW_2); 

            			newLeafNode(otherlv_7, grammarAccess.getEntityAccess().getRightCurlyBracketKeyword_6());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleEntity"


    // $ANTLR start "entryRuleState"
    // InternalBdd.g:260:1: entryRuleState returns [EObject current=null] : iv_ruleState= ruleState EOF ;
    public final EObject entryRuleState() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleState = null;


        try {
            // InternalBdd.g:260:46: (iv_ruleState= ruleState EOF )
            // InternalBdd.g:261:2: iv_ruleState= ruleState EOF
            {
             newCompositeNode(grammarAccess.getStateRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleState=ruleState();

            state._fsp--;

             current =iv_ruleState; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleState"


    // $ANTLR start "ruleState"
    // InternalBdd.g:267:1: ruleState returns [EObject current=null] : ( (lv_name_0_0= RULE_ID ) ) ;
    public final EObject ruleState() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_0=null;


        	enterRule();

        try {
            // InternalBdd.g:273:2: ( ( (lv_name_0_0= RULE_ID ) ) )
            // InternalBdd.g:274:2: ( (lv_name_0_0= RULE_ID ) )
            {
            // InternalBdd.g:274:2: ( (lv_name_0_0= RULE_ID ) )
            // InternalBdd.g:275:3: (lv_name_0_0= RULE_ID )
            {
            // InternalBdd.g:275:3: (lv_name_0_0= RULE_ID )
            // InternalBdd.g:276:4: lv_name_0_0= RULE_ID
            {
            lv_name_0_0=(Token)match(input,RULE_ID,FOLLOW_2); 

            				newLeafNode(lv_name_0_0, grammarAccess.getStateAccess().getNameIDTerminalRuleCall_0());
            			

            				if (current==null) {
            					current = createModelElement(grammarAccess.getStateRule());
            				}
            				setWithLastConsumed(
            					current,
            					"name",
            					lv_name_0_0,
            					"org.eclipse.xtext.common.Terminals.ID");
            			

            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleState"


    // $ANTLR start "entryRuleProperty"
    // InternalBdd.g:295:1: entryRuleProperty returns [EObject current=null] : iv_ruleProperty= ruleProperty EOF ;
    public final EObject entryRuleProperty() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleProperty = null;


        try {
            // InternalBdd.g:295:49: (iv_ruleProperty= ruleProperty EOF )
            // InternalBdd.g:296:2: iv_ruleProperty= ruleProperty EOF
            {
             newCompositeNode(grammarAccess.getPropertyRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleProperty=ruleProperty();

            state._fsp--;

             current =iv_ruleProperty; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleProperty"


    // $ANTLR start "ruleProperty"
    // InternalBdd.g:302:1: ruleProperty returns [EObject current=null] : ( ( (lv_name_0_0= RULE_ID ) ) otherlv_1= '=' ( (lv_value_2_0= RULE_INT ) ) ) ;
    public final EObject ruleProperty() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_0=null;
        Token otherlv_1=null;
        Token lv_value_2_0=null;


        	enterRule();

        try {
            // InternalBdd.g:308:2: ( ( ( (lv_name_0_0= RULE_ID ) ) otherlv_1= '=' ( (lv_value_2_0= RULE_INT ) ) ) )
            // InternalBdd.g:309:2: ( ( (lv_name_0_0= RULE_ID ) ) otherlv_1= '=' ( (lv_value_2_0= RULE_INT ) ) )
            {
            // InternalBdd.g:309:2: ( ( (lv_name_0_0= RULE_ID ) ) otherlv_1= '=' ( (lv_value_2_0= RULE_INT ) ) )
            // InternalBdd.g:310:3: ( (lv_name_0_0= RULE_ID ) ) otherlv_1= '=' ( (lv_value_2_0= RULE_INT ) )
            {
            // InternalBdd.g:310:3: ( (lv_name_0_0= RULE_ID ) )
            // InternalBdd.g:311:4: (lv_name_0_0= RULE_ID )
            {
            // InternalBdd.g:311:4: (lv_name_0_0= RULE_ID )
            // InternalBdd.g:312:5: lv_name_0_0= RULE_ID
            {
            lv_name_0_0=(Token)match(input,RULE_ID,FOLLOW_12); 

            					newLeafNode(lv_name_0_0, grammarAccess.getPropertyAccess().getNameIDTerminalRuleCall_0_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getPropertyRule());
            					}
            					setWithLastConsumed(
            						current,
            						"name",
            						lv_name_0_0,
            						"org.eclipse.xtext.common.Terminals.ID");
            				

            }


            }

            otherlv_1=(Token)match(input,17,FOLLOW_13); 

            			newLeafNode(otherlv_1, grammarAccess.getPropertyAccess().getEqualsSignKeyword_1());
            		
            // InternalBdd.g:332:3: ( (lv_value_2_0= RULE_INT ) )
            // InternalBdd.g:333:4: (lv_value_2_0= RULE_INT )
            {
            // InternalBdd.g:333:4: (lv_value_2_0= RULE_INT )
            // InternalBdd.g:334:5: lv_value_2_0= RULE_INT
            {
            lv_value_2_0=(Token)match(input,RULE_INT,FOLLOW_2); 

            					newLeafNode(lv_value_2_0, grammarAccess.getPropertyAccess().getValueINTTerminalRuleCall_2_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getPropertyRule());
            					}
            					setWithLastConsumed(
            						current,
            						"value",
            						lv_value_2_0,
            						"org.eclipse.xtext.common.Terminals.INT");
            				

            }


            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleProperty"


    // $ANTLR start "entryRuleScenario"
    // InternalBdd.g:354:1: entryRuleScenario returns [EObject current=null] : iv_ruleScenario= ruleScenario EOF ;
    public final EObject entryRuleScenario() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleScenario = null;


        try {
            // InternalBdd.g:354:49: (iv_ruleScenario= ruleScenario EOF )
            // InternalBdd.g:355:2: iv_ruleScenario= ruleScenario EOF
            {
             newCompositeNode(grammarAccess.getScenarioRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleScenario=ruleScenario();

            state._fsp--;

             current =iv_ruleScenario; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleScenario"


    // $ANTLR start "ruleScenario"
    // InternalBdd.g:361:1: ruleScenario returns [EObject current=null] : (otherlv_0= 'scenario:' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= 'given' ( (lv_guards_3_0= ruleCondition ) ) (otherlv_4= 'and' ( (lv_guards_5_0= ruleCondition ) ) )* ( (lv_trigger_6_0= ruleTrigger ) ) otherlv_7= 'then' ( (lv_effects_8_0= ruleEffect ) ) (otherlv_9= 'and' ( (lv_effects_10_0= ruleEffect ) ) )* ) ;
    public final EObject ruleScenario() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        Token otherlv_7=null;
        Token otherlv_9=null;
        EObject lv_guards_3_0 = null;

        EObject lv_guards_5_0 = null;

        EObject lv_trigger_6_0 = null;

        EObject lv_effects_8_0 = null;

        EObject lv_effects_10_0 = null;



        	enterRule();

        try {
            // InternalBdd.g:367:2: ( (otherlv_0= 'scenario:' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= 'given' ( (lv_guards_3_0= ruleCondition ) ) (otherlv_4= 'and' ( (lv_guards_5_0= ruleCondition ) ) )* ( (lv_trigger_6_0= ruleTrigger ) ) otherlv_7= 'then' ( (lv_effects_8_0= ruleEffect ) ) (otherlv_9= 'and' ( (lv_effects_10_0= ruleEffect ) ) )* ) )
            // InternalBdd.g:368:2: (otherlv_0= 'scenario:' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= 'given' ( (lv_guards_3_0= ruleCondition ) ) (otherlv_4= 'and' ( (lv_guards_5_0= ruleCondition ) ) )* ( (lv_trigger_6_0= ruleTrigger ) ) otherlv_7= 'then' ( (lv_effects_8_0= ruleEffect ) ) (otherlv_9= 'and' ( (lv_effects_10_0= ruleEffect ) ) )* )
            {
            // InternalBdd.g:368:2: (otherlv_0= 'scenario:' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= 'given' ( (lv_guards_3_0= ruleCondition ) ) (otherlv_4= 'and' ( (lv_guards_5_0= ruleCondition ) ) )* ( (lv_trigger_6_0= ruleTrigger ) ) otherlv_7= 'then' ( (lv_effects_8_0= ruleEffect ) ) (otherlv_9= 'and' ( (lv_effects_10_0= ruleEffect ) ) )* )
            // InternalBdd.g:369:3: otherlv_0= 'scenario:' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= 'given' ( (lv_guards_3_0= ruleCondition ) ) (otherlv_4= 'and' ( (lv_guards_5_0= ruleCondition ) ) )* ( (lv_trigger_6_0= ruleTrigger ) ) otherlv_7= 'then' ( (lv_effects_8_0= ruleEffect ) ) (otherlv_9= 'and' ( (lv_effects_10_0= ruleEffect ) ) )*
            {
            otherlv_0=(Token)match(input,18,FOLLOW_3); 

            			newLeafNode(otherlv_0, grammarAccess.getScenarioAccess().getScenarioKeyword_0());
            		
            // InternalBdd.g:373:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalBdd.g:374:4: (lv_name_1_0= RULE_ID )
            {
            // InternalBdd.g:374:4: (lv_name_1_0= RULE_ID )
            // InternalBdd.g:375:5: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_14); 

            					newLeafNode(lv_name_1_0, grammarAccess.getScenarioAccess().getNameIDTerminalRuleCall_1_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getScenarioRule());
            					}
            					setWithLastConsumed(
            						current,
            						"name",
            						lv_name_1_0,
            						"org.eclipse.xtext.common.Terminals.ID");
            				

            }


            }

            otherlv_2=(Token)match(input,19,FOLLOW_3); 

            			newLeafNode(otherlv_2, grammarAccess.getScenarioAccess().getGivenKeyword_2());
            		
            // InternalBdd.g:395:3: ( (lv_guards_3_0= ruleCondition ) )
            // InternalBdd.g:396:4: (lv_guards_3_0= ruleCondition )
            {
            // InternalBdd.g:396:4: (lv_guards_3_0= ruleCondition )
            // InternalBdd.g:397:5: lv_guards_3_0= ruleCondition
            {

            					newCompositeNode(grammarAccess.getScenarioAccess().getGuardsConditionParserRuleCall_3_0());
            				
            pushFollow(FOLLOW_15);
            lv_guards_3_0=ruleCondition();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getScenarioRule());
            					}
            					add(
            						current,
            						"guards",
            						lv_guards_3_0,
            						"dk.sdu.Bdd.Condition");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalBdd.g:414:3: (otherlv_4= 'and' ( (lv_guards_5_0= ruleCondition ) ) )*
            loop7:
            do {
                int alt7=2;
                int LA7_0 = input.LA(1);

                if ( (LA7_0==20) ) {
                    alt7=1;
                }


                switch (alt7) {
            	case 1 :
            	    // InternalBdd.g:415:4: otherlv_4= 'and' ( (lv_guards_5_0= ruleCondition ) )
            	    {
            	    otherlv_4=(Token)match(input,20,FOLLOW_3); 

            	    				newLeafNode(otherlv_4, grammarAccess.getScenarioAccess().getAndKeyword_4_0());
            	    			
            	    // InternalBdd.g:419:4: ( (lv_guards_5_0= ruleCondition ) )
            	    // InternalBdd.g:420:5: (lv_guards_5_0= ruleCondition )
            	    {
            	    // InternalBdd.g:420:5: (lv_guards_5_0= ruleCondition )
            	    // InternalBdd.g:421:6: lv_guards_5_0= ruleCondition
            	    {

            	    						newCompositeNode(grammarAccess.getScenarioAccess().getGuardsConditionParserRuleCall_4_1_0());
            	    					
            	    pushFollow(FOLLOW_15);
            	    lv_guards_5_0=ruleCondition();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getScenarioRule());
            	    						}
            	    						add(
            	    							current,
            	    							"guards",
            	    							lv_guards_5_0,
            	    							"dk.sdu.Bdd.Condition");
            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop7;
                }
            } while (true);

            // InternalBdd.g:439:3: ( (lv_trigger_6_0= ruleTrigger ) )
            // InternalBdd.g:440:4: (lv_trigger_6_0= ruleTrigger )
            {
            // InternalBdd.g:440:4: (lv_trigger_6_0= ruleTrigger )
            // InternalBdd.g:441:5: lv_trigger_6_0= ruleTrigger
            {

            					newCompositeNode(grammarAccess.getScenarioAccess().getTriggerTriggerParserRuleCall_5_0());
            				
            pushFollow(FOLLOW_16);
            lv_trigger_6_0=ruleTrigger();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getScenarioRule());
            					}
            					set(
            						current,
            						"trigger",
            						lv_trigger_6_0,
            						"dk.sdu.Bdd.Trigger");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_7=(Token)match(input,21,FOLLOW_3); 

            			newLeafNode(otherlv_7, grammarAccess.getScenarioAccess().getThenKeyword_6());
            		
            // InternalBdd.g:462:3: ( (lv_effects_8_0= ruleEffect ) )
            // InternalBdd.g:463:4: (lv_effects_8_0= ruleEffect )
            {
            // InternalBdd.g:463:4: (lv_effects_8_0= ruleEffect )
            // InternalBdd.g:464:5: lv_effects_8_0= ruleEffect
            {

            					newCompositeNode(grammarAccess.getScenarioAccess().getEffectsEffectParserRuleCall_7_0());
            				
            pushFollow(FOLLOW_17);
            lv_effects_8_0=ruleEffect();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getScenarioRule());
            					}
            					add(
            						current,
            						"effects",
            						lv_effects_8_0,
            						"dk.sdu.Bdd.Effect");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalBdd.g:481:3: (otherlv_9= 'and' ( (lv_effects_10_0= ruleEffect ) ) )*
            loop8:
            do {
                int alt8=2;
                int LA8_0 = input.LA(1);

                if ( (LA8_0==20) ) {
                    alt8=1;
                }


                switch (alt8) {
            	case 1 :
            	    // InternalBdd.g:482:4: otherlv_9= 'and' ( (lv_effects_10_0= ruleEffect ) )
            	    {
            	    otherlv_9=(Token)match(input,20,FOLLOW_3); 

            	    				newLeafNode(otherlv_9, grammarAccess.getScenarioAccess().getAndKeyword_8_0());
            	    			
            	    // InternalBdd.g:486:4: ( (lv_effects_10_0= ruleEffect ) )
            	    // InternalBdd.g:487:5: (lv_effects_10_0= ruleEffect )
            	    {
            	    // InternalBdd.g:487:5: (lv_effects_10_0= ruleEffect )
            	    // InternalBdd.g:488:6: lv_effects_10_0= ruleEffect
            	    {

            	    						newCompositeNode(grammarAccess.getScenarioAccess().getEffectsEffectParserRuleCall_8_1_0());
            	    					
            	    pushFollow(FOLLOW_17);
            	    lv_effects_10_0=ruleEffect();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getScenarioRule());
            	    						}
            	    						add(
            	    							current,
            	    							"effects",
            	    							lv_effects_10_0,
            	    							"dk.sdu.Bdd.Effect");
            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop8;
                }
            } while (true);


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleScenario"


    // $ANTLR start "entryRuleTrigger"
    // InternalBdd.g:510:1: entryRuleTrigger returns [EObject current=null] : iv_ruleTrigger= ruleTrigger EOF ;
    public final EObject entryRuleTrigger() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTrigger = null;


        try {
            // InternalBdd.g:510:48: (iv_ruleTrigger= ruleTrigger EOF )
            // InternalBdd.g:511:2: iv_ruleTrigger= ruleTrigger EOF
            {
             newCompositeNode(grammarAccess.getTriggerRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleTrigger=ruleTrigger();

            state._fsp--;

             current =iv_ruleTrigger; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleTrigger"


    // $ANTLR start "ruleTrigger"
    // InternalBdd.g:517:1: ruleTrigger returns [EObject current=null] : (this_WhenTrigger_0= ruleWhenTrigger | this_WheneverTrigger_1= ruleWheneverTrigger ) ;
    public final EObject ruleTrigger() throws RecognitionException {
        EObject current = null;

        EObject this_WhenTrigger_0 = null;

        EObject this_WheneverTrigger_1 = null;



        	enterRule();

        try {
            // InternalBdd.g:523:2: ( (this_WhenTrigger_0= ruleWhenTrigger | this_WheneverTrigger_1= ruleWheneverTrigger ) )
            // InternalBdd.g:524:2: (this_WhenTrigger_0= ruleWhenTrigger | this_WheneverTrigger_1= ruleWheneverTrigger )
            {
            // InternalBdd.g:524:2: (this_WhenTrigger_0= ruleWhenTrigger | this_WheneverTrigger_1= ruleWheneverTrigger )
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0==22) ) {
                alt9=1;
            }
            else if ( (LA9_0==23) ) {
                alt9=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 9, 0, input);

                throw nvae;
            }
            switch (alt9) {
                case 1 :
                    // InternalBdd.g:525:3: this_WhenTrigger_0= ruleWhenTrigger
                    {

                    			newCompositeNode(grammarAccess.getTriggerAccess().getWhenTriggerParserRuleCall_0());
                    		
                    pushFollow(FOLLOW_2);
                    this_WhenTrigger_0=ruleWhenTrigger();

                    state._fsp--;


                    			current = this_WhenTrigger_0;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 2 :
                    // InternalBdd.g:534:3: this_WheneverTrigger_1= ruleWheneverTrigger
                    {

                    			newCompositeNode(grammarAccess.getTriggerAccess().getWheneverTriggerParserRuleCall_1());
                    		
                    pushFollow(FOLLOW_2);
                    this_WheneverTrigger_1=ruleWheneverTrigger();

                    state._fsp--;


                    			current = this_WheneverTrigger_1;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleTrigger"


    // $ANTLR start "entryRuleWhenTrigger"
    // InternalBdd.g:546:1: entryRuleWhenTrigger returns [EObject current=null] : iv_ruleWhenTrigger= ruleWhenTrigger EOF ;
    public final EObject entryRuleWhenTrigger() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleWhenTrigger = null;


        try {
            // InternalBdd.g:546:52: (iv_ruleWhenTrigger= ruleWhenTrigger EOF )
            // InternalBdd.g:547:2: iv_ruleWhenTrigger= ruleWhenTrigger EOF
            {
             newCompositeNode(grammarAccess.getWhenTriggerRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleWhenTrigger=ruleWhenTrigger();

            state._fsp--;

             current =iv_ruleWhenTrigger; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleWhenTrigger"


    // $ANTLR start "ruleWhenTrigger"
    // InternalBdd.g:553:1: ruleWhenTrigger returns [EObject current=null] : (otherlv_0= 'when' ( (lv_event_1_0= ruleEvent ) ) ) ;
    public final EObject ruleWhenTrigger() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        EObject lv_event_1_0 = null;



        	enterRule();

        try {
            // InternalBdd.g:559:2: ( (otherlv_0= 'when' ( (lv_event_1_0= ruleEvent ) ) ) )
            // InternalBdd.g:560:2: (otherlv_0= 'when' ( (lv_event_1_0= ruleEvent ) ) )
            {
            // InternalBdd.g:560:2: (otherlv_0= 'when' ( (lv_event_1_0= ruleEvent ) ) )
            // InternalBdd.g:561:3: otherlv_0= 'when' ( (lv_event_1_0= ruleEvent ) )
            {
            otherlv_0=(Token)match(input,22,FOLLOW_18); 

            			newLeafNode(otherlv_0, grammarAccess.getWhenTriggerAccess().getWhenKeyword_0());
            		
            // InternalBdd.g:565:3: ( (lv_event_1_0= ruleEvent ) )
            // InternalBdd.g:566:4: (lv_event_1_0= ruleEvent )
            {
            // InternalBdd.g:566:4: (lv_event_1_0= ruleEvent )
            // InternalBdd.g:567:5: lv_event_1_0= ruleEvent
            {

            					newCompositeNode(grammarAccess.getWhenTriggerAccess().getEventEventParserRuleCall_1_0());
            				
            pushFollow(FOLLOW_2);
            lv_event_1_0=ruleEvent();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getWhenTriggerRule());
            					}
            					set(
            						current,
            						"event",
            						lv_event_1_0,
            						"dk.sdu.Bdd.Event");
            					afterParserOrEnumRuleCall();
            				

            }


            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleWhenTrigger"


    // $ANTLR start "entryRuleWheneverTrigger"
    // InternalBdd.g:588:1: entryRuleWheneverTrigger returns [EObject current=null] : iv_ruleWheneverTrigger= ruleWheneverTrigger EOF ;
    public final EObject entryRuleWheneverTrigger() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleWheneverTrigger = null;


        try {
            // InternalBdd.g:588:56: (iv_ruleWheneverTrigger= ruleWheneverTrigger EOF )
            // InternalBdd.g:589:2: iv_ruleWheneverTrigger= ruleWheneverTrigger EOF
            {
             newCompositeNode(grammarAccess.getWheneverTriggerRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleWheneverTrigger=ruleWheneverTrigger();

            state._fsp--;

             current =iv_ruleWheneverTrigger; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleWheneverTrigger"


    // $ANTLR start "ruleWheneverTrigger"
    // InternalBdd.g:595:1: ruleWheneverTrigger returns [EObject current=null] : (otherlv_0= 'whenever' ( (lv_event_1_0= ruleEvent ) ) ) ;
    public final EObject ruleWheneverTrigger() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        EObject lv_event_1_0 = null;



        	enterRule();

        try {
            // InternalBdd.g:601:2: ( (otherlv_0= 'whenever' ( (lv_event_1_0= ruleEvent ) ) ) )
            // InternalBdd.g:602:2: (otherlv_0= 'whenever' ( (lv_event_1_0= ruleEvent ) ) )
            {
            // InternalBdd.g:602:2: (otherlv_0= 'whenever' ( (lv_event_1_0= ruleEvent ) ) )
            // InternalBdd.g:603:3: otherlv_0= 'whenever' ( (lv_event_1_0= ruleEvent ) )
            {
            otherlv_0=(Token)match(input,23,FOLLOW_18); 

            			newLeafNode(otherlv_0, grammarAccess.getWheneverTriggerAccess().getWheneverKeyword_0());
            		
            // InternalBdd.g:607:3: ( (lv_event_1_0= ruleEvent ) )
            // InternalBdd.g:608:4: (lv_event_1_0= ruleEvent )
            {
            // InternalBdd.g:608:4: (lv_event_1_0= ruleEvent )
            // InternalBdd.g:609:5: lv_event_1_0= ruleEvent
            {

            					newCompositeNode(grammarAccess.getWheneverTriggerAccess().getEventEventParserRuleCall_1_0());
            				
            pushFollow(FOLLOW_2);
            lv_event_1_0=ruleEvent();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getWheneverTriggerRule());
            					}
            					set(
            						current,
            						"event",
            						lv_event_1_0,
            						"dk.sdu.Bdd.Event");
            					afterParserOrEnumRuleCall();
            				

            }


            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleWheneverTrigger"


    // $ANTLR start "entryRuleEvent"
    // InternalBdd.g:630:1: entryRuleEvent returns [EObject current=null] : iv_ruleEvent= ruleEvent EOF ;
    public final EObject entryRuleEvent() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleEvent = null;


        try {
            // InternalBdd.g:630:46: (iv_ruleEvent= ruleEvent EOF )
            // InternalBdd.g:631:2: iv_ruleEvent= ruleEvent EOF
            {
             newCompositeNode(grammarAccess.getEventRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleEvent=ruleEvent();

            state._fsp--;

             current =iv_ruleEvent; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleEvent"


    // $ANTLR start "ruleEvent"
    // InternalBdd.g:637:1: ruleEvent returns [EObject current=null] : ( ( ( (lv_time_0_0= ruleTimeEvent ) ) otherlv_1= 'and' ( (lv_end_2_0= ruleEndEvent ) ) ) | ( (lv_time_3_0= ruleTimeEvent ) ) | ( (lv_end_4_0= ruleEndEvent ) ) ) ;
    public final EObject ruleEvent() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        EObject lv_time_0_0 = null;

        EObject lv_end_2_0 = null;

        EObject lv_time_3_0 = null;

        EObject lv_end_4_0 = null;



        	enterRule();

        try {
            // InternalBdd.g:643:2: ( ( ( ( (lv_time_0_0= ruleTimeEvent ) ) otherlv_1= 'and' ( (lv_end_2_0= ruleEndEvent ) ) ) | ( (lv_time_3_0= ruleTimeEvent ) ) | ( (lv_end_4_0= ruleEndEvent ) ) ) )
            // InternalBdd.g:644:2: ( ( ( (lv_time_0_0= ruleTimeEvent ) ) otherlv_1= 'and' ( (lv_end_2_0= ruleEndEvent ) ) ) | ( (lv_time_3_0= ruleTimeEvent ) ) | ( (lv_end_4_0= ruleEndEvent ) ) )
            {
            // InternalBdd.g:644:2: ( ( ( (lv_time_0_0= ruleTimeEvent ) ) otherlv_1= 'and' ( (lv_end_2_0= ruleEndEvent ) ) ) | ( (lv_time_3_0= ruleTimeEvent ) ) | ( (lv_end_4_0= ruleEndEvent ) ) )
            int alt10=3;
            alt10 = dfa10.predict(input);
            switch (alt10) {
                case 1 :
                    // InternalBdd.g:645:3: ( ( (lv_time_0_0= ruleTimeEvent ) ) otherlv_1= 'and' ( (lv_end_2_0= ruleEndEvent ) ) )
                    {
                    // InternalBdd.g:645:3: ( ( (lv_time_0_0= ruleTimeEvent ) ) otherlv_1= 'and' ( (lv_end_2_0= ruleEndEvent ) ) )
                    // InternalBdd.g:646:4: ( (lv_time_0_0= ruleTimeEvent ) ) otherlv_1= 'and' ( (lv_end_2_0= ruleEndEvent ) )
                    {
                    // InternalBdd.g:646:4: ( (lv_time_0_0= ruleTimeEvent ) )
                    // InternalBdd.g:647:5: (lv_time_0_0= ruleTimeEvent )
                    {
                    // InternalBdd.g:647:5: (lv_time_0_0= ruleTimeEvent )
                    // InternalBdd.g:648:6: lv_time_0_0= ruleTimeEvent
                    {

                    						newCompositeNode(grammarAccess.getEventAccess().getTimeTimeEventParserRuleCall_0_0_0());
                    					
                    pushFollow(FOLLOW_19);
                    lv_time_0_0=ruleTimeEvent();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getEventRule());
                    						}
                    						set(
                    							current,
                    							"time",
                    							lv_time_0_0,
                    							"dk.sdu.Bdd.TimeEvent");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }

                    otherlv_1=(Token)match(input,20,FOLLOW_18); 

                    				newLeafNode(otherlv_1, grammarAccess.getEventAccess().getAndKeyword_0_1());
                    			
                    // InternalBdd.g:669:4: ( (lv_end_2_0= ruleEndEvent ) )
                    // InternalBdd.g:670:5: (lv_end_2_0= ruleEndEvent )
                    {
                    // InternalBdd.g:670:5: (lv_end_2_0= ruleEndEvent )
                    // InternalBdd.g:671:6: lv_end_2_0= ruleEndEvent
                    {

                    						newCompositeNode(grammarAccess.getEventAccess().getEndEndEventParserRuleCall_0_2_0());
                    					
                    pushFollow(FOLLOW_2);
                    lv_end_2_0=ruleEndEvent();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getEventRule());
                    						}
                    						set(
                    							current,
                    							"end",
                    							lv_end_2_0,
                    							"dk.sdu.Bdd.EndEvent");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalBdd.g:690:3: ( (lv_time_3_0= ruleTimeEvent ) )
                    {
                    // InternalBdd.g:690:3: ( (lv_time_3_0= ruleTimeEvent ) )
                    // InternalBdd.g:691:4: (lv_time_3_0= ruleTimeEvent )
                    {
                    // InternalBdd.g:691:4: (lv_time_3_0= ruleTimeEvent )
                    // InternalBdd.g:692:5: lv_time_3_0= ruleTimeEvent
                    {

                    					newCompositeNode(grammarAccess.getEventAccess().getTimeTimeEventParserRuleCall_1_0());
                    				
                    pushFollow(FOLLOW_2);
                    lv_time_3_0=ruleTimeEvent();

                    state._fsp--;


                    					if (current==null) {
                    						current = createModelElementForParent(grammarAccess.getEventRule());
                    					}
                    					set(
                    						current,
                    						"time",
                    						lv_time_3_0,
                    						"dk.sdu.Bdd.TimeEvent");
                    					afterParserOrEnumRuleCall();
                    				

                    }


                    }


                    }
                    break;
                case 3 :
                    // InternalBdd.g:710:3: ( (lv_end_4_0= ruleEndEvent ) )
                    {
                    // InternalBdd.g:710:3: ( (lv_end_4_0= ruleEndEvent ) )
                    // InternalBdd.g:711:4: (lv_end_4_0= ruleEndEvent )
                    {
                    // InternalBdd.g:711:4: (lv_end_4_0= ruleEndEvent )
                    // InternalBdd.g:712:5: lv_end_4_0= ruleEndEvent
                    {

                    					newCompositeNode(grammarAccess.getEventAccess().getEndEndEventParserRuleCall_2_0());
                    				
                    pushFollow(FOLLOW_2);
                    lv_end_4_0=ruleEndEvent();

                    state._fsp--;


                    					if (current==null) {
                    						current = createModelElementForParent(grammarAccess.getEventRule());
                    					}
                    					set(
                    						current,
                    						"end",
                    						lv_end_4_0,
                    						"dk.sdu.Bdd.EndEvent");
                    					afterParserOrEnumRuleCall();
                    				

                    }


                    }


                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleEvent"


    // $ANTLR start "entryRuleTimeEvent"
    // InternalBdd.g:733:1: entryRuleTimeEvent returns [EObject current=null] : iv_ruleTimeEvent= ruleTimeEvent EOF ;
    public final EObject entryRuleTimeEvent() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTimeEvent = null;


        try {
            // InternalBdd.g:733:50: (iv_ruleTimeEvent= ruleTimeEvent EOF )
            // InternalBdd.g:734:2: iv_ruleTimeEvent= ruleTimeEvent EOF
            {
             newCompositeNode(grammarAccess.getTimeEventRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleTimeEvent=ruleTimeEvent();

            state._fsp--;

             current =iv_ruleTimeEvent; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleTimeEvent"


    // $ANTLR start "ruleTimeEvent"
    // InternalBdd.g:740:1: ruleTimeEvent returns [EObject current=null] : ( ( (lv_amount_0_0= RULE_INT ) ) ( (lv_unit_1_0= ruleTimeUnit ) ) otherlv_2= 'have' otherlv_3= 'passed' ) ;
    public final EObject ruleTimeEvent() throws RecognitionException {
        EObject current = null;

        Token lv_amount_0_0=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Enumerator lv_unit_1_0 = null;



        	enterRule();

        try {
            // InternalBdd.g:746:2: ( ( ( (lv_amount_0_0= RULE_INT ) ) ( (lv_unit_1_0= ruleTimeUnit ) ) otherlv_2= 'have' otherlv_3= 'passed' ) )
            // InternalBdd.g:747:2: ( ( (lv_amount_0_0= RULE_INT ) ) ( (lv_unit_1_0= ruleTimeUnit ) ) otherlv_2= 'have' otherlv_3= 'passed' )
            {
            // InternalBdd.g:747:2: ( ( (lv_amount_0_0= RULE_INT ) ) ( (lv_unit_1_0= ruleTimeUnit ) ) otherlv_2= 'have' otherlv_3= 'passed' )
            // InternalBdd.g:748:3: ( (lv_amount_0_0= RULE_INT ) ) ( (lv_unit_1_0= ruleTimeUnit ) ) otherlv_2= 'have' otherlv_3= 'passed'
            {
            // InternalBdd.g:748:3: ( (lv_amount_0_0= RULE_INT ) )
            // InternalBdd.g:749:4: (lv_amount_0_0= RULE_INT )
            {
            // InternalBdd.g:749:4: (lv_amount_0_0= RULE_INT )
            // InternalBdd.g:750:5: lv_amount_0_0= RULE_INT
            {
            lv_amount_0_0=(Token)match(input,RULE_INT,FOLLOW_20); 

            					newLeafNode(lv_amount_0_0, grammarAccess.getTimeEventAccess().getAmountINTTerminalRuleCall_0_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getTimeEventRule());
            					}
            					setWithLastConsumed(
            						current,
            						"amount",
            						lv_amount_0_0,
            						"org.eclipse.xtext.common.Terminals.INT");
            				

            }


            }

            // InternalBdd.g:766:3: ( (lv_unit_1_0= ruleTimeUnit ) )
            // InternalBdd.g:767:4: (lv_unit_1_0= ruleTimeUnit )
            {
            // InternalBdd.g:767:4: (lv_unit_1_0= ruleTimeUnit )
            // InternalBdd.g:768:5: lv_unit_1_0= ruleTimeUnit
            {

            					newCompositeNode(grammarAccess.getTimeEventAccess().getUnitTimeUnitEnumRuleCall_1_0());
            				
            pushFollow(FOLLOW_21);
            lv_unit_1_0=ruleTimeUnit();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getTimeEventRule());
            					}
            					set(
            						current,
            						"unit",
            						lv_unit_1_0,
            						"dk.sdu.Bdd.TimeUnit");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_2=(Token)match(input,24,FOLLOW_22); 

            			newLeafNode(otherlv_2, grammarAccess.getTimeEventAccess().getHaveKeyword_2());
            		
            otherlv_3=(Token)match(input,25,FOLLOW_2); 

            			newLeafNode(otherlv_3, grammarAccess.getTimeEventAccess().getPassedKeyword_3());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleTimeEvent"


    // $ANTLR start "entryRuleEndEvent"
    // InternalBdd.g:797:1: entryRuleEndEvent returns [EObject current=null] : iv_ruleEndEvent= ruleEndEvent EOF ;
    public final EObject entryRuleEndEvent() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleEndEvent = null;


        try {
            // InternalBdd.g:797:49: (iv_ruleEndEvent= ruleEndEvent EOF )
            // InternalBdd.g:798:2: iv_ruleEndEvent= ruleEndEvent EOF
            {
             newCompositeNode(grammarAccess.getEndEventRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleEndEvent=ruleEndEvent();

            state._fsp--;

             current =iv_ruleEndEvent; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleEndEvent"


    // $ANTLR start "ruleEndEvent"
    // InternalBdd.g:804:1: ruleEndEvent returns [EObject current=null] : ( ( (otherlv_0= RULE_ID ) ) ( (otherlv_1= RULE_ID ) ) otherlv_2= 'finishes' ) ;
    public final EObject ruleEndEvent() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;


        	enterRule();

        try {
            // InternalBdd.g:810:2: ( ( ( (otherlv_0= RULE_ID ) ) ( (otherlv_1= RULE_ID ) ) otherlv_2= 'finishes' ) )
            // InternalBdd.g:811:2: ( ( (otherlv_0= RULE_ID ) ) ( (otherlv_1= RULE_ID ) ) otherlv_2= 'finishes' )
            {
            // InternalBdd.g:811:2: ( ( (otherlv_0= RULE_ID ) ) ( (otherlv_1= RULE_ID ) ) otherlv_2= 'finishes' )
            // InternalBdd.g:812:3: ( (otherlv_0= RULE_ID ) ) ( (otherlv_1= RULE_ID ) ) otherlv_2= 'finishes'
            {
            // InternalBdd.g:812:3: ( (otherlv_0= RULE_ID ) )
            // InternalBdd.g:813:4: (otherlv_0= RULE_ID )
            {
            // InternalBdd.g:813:4: (otherlv_0= RULE_ID )
            // InternalBdd.g:814:5: otherlv_0= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getEndEventRule());
            					}
            				
            otherlv_0=(Token)match(input,RULE_ID,FOLLOW_3); 

            					newLeafNode(otherlv_0, grammarAccess.getEndEventAccess().getEntityEntityCrossReference_0_0());
            				

            }


            }

            // InternalBdd.g:825:3: ( (otherlv_1= RULE_ID ) )
            // InternalBdd.g:826:4: (otherlv_1= RULE_ID )
            {
            // InternalBdd.g:826:4: (otherlv_1= RULE_ID )
            // InternalBdd.g:827:5: otherlv_1= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getEndEventRule());
            					}
            				
            otherlv_1=(Token)match(input,RULE_ID,FOLLOW_23); 

            					newLeafNode(otherlv_1, grammarAccess.getEndEventAccess().getStateStateCrossReference_1_0());
            				

            }


            }

            otherlv_2=(Token)match(input,26,FOLLOW_2); 

            			newLeafNode(otherlv_2, grammarAccess.getEndEventAccess().getFinishesKeyword_2());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleEndEvent"


    // $ANTLR start "entryRuleCondition"
    // InternalBdd.g:846:1: entryRuleCondition returns [EObject current=null] : iv_ruleCondition= ruleCondition EOF ;
    public final EObject entryRuleCondition() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleCondition = null;


        try {
            // InternalBdd.g:846:50: (iv_ruleCondition= ruleCondition EOF )
            // InternalBdd.g:847:2: iv_ruleCondition= ruleCondition EOF
            {
             newCompositeNode(grammarAccess.getConditionRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleCondition=ruleCondition();

            state._fsp--;

             current =iv_ruleCondition; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleCondition"


    // $ANTLR start "ruleCondition"
    // InternalBdd.g:853:1: ruleCondition returns [EObject current=null] : (this_StateCondition_0= ruleStateCondition | this_PropCondition_1= rulePropCondition | this_BetweenCondition_2= ruleBetweenCondition ) ;
    public final EObject ruleCondition() throws RecognitionException {
        EObject current = null;

        EObject this_StateCondition_0 = null;

        EObject this_PropCondition_1 = null;

        EObject this_BetweenCondition_2 = null;



        	enterRule();

        try {
            // InternalBdd.g:859:2: ( (this_StateCondition_0= ruleStateCondition | this_PropCondition_1= rulePropCondition | this_BetweenCondition_2= ruleBetweenCondition ) )
            // InternalBdd.g:860:2: (this_StateCondition_0= ruleStateCondition | this_PropCondition_1= rulePropCondition | this_BetweenCondition_2= ruleBetweenCondition )
            {
            // InternalBdd.g:860:2: (this_StateCondition_0= ruleStateCondition | this_PropCondition_1= rulePropCondition | this_BetweenCondition_2= ruleBetweenCondition )
            int alt11=3;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==RULE_ID) ) {
                int LA11_1 = input.LA(2);

                if ( (LA11_1==27) ) {
                    alt11=1;
                }
                else if ( (LA11_1==33) ) {
                    int LA11_3 = input.LA(3);

                    if ( (LA11_3==RULE_ID) ) {
                        int LA11_4 = input.LA(4);

                        if ( (LA11_4==27) ) {
                            int LA11_5 = input.LA(5);

                            if ( (LA11_5==30) ) {
                                alt11=3;
                            }
                            else if ( ((LA11_5>=RULE_ID && LA11_5<=RULE_INT)) ) {
                                alt11=2;
                            }
                            else {
                                NoViableAltException nvae =
                                    new NoViableAltException("", 11, 5, input);

                                throw nvae;
                            }
                        }
                        else if ( ((LA11_4>=55 && LA11_4<=59)) ) {
                            alt11=2;
                        }
                        else {
                            NoViableAltException nvae =
                                new NoViableAltException("", 11, 4, input);

                            throw nvae;
                        }
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 11, 3, input);

                        throw nvae;
                    }
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 11, 1, input);

                    throw nvae;
                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 11, 0, input);

                throw nvae;
            }
            switch (alt11) {
                case 1 :
                    // InternalBdd.g:861:3: this_StateCondition_0= ruleStateCondition
                    {

                    			newCompositeNode(grammarAccess.getConditionAccess().getStateConditionParserRuleCall_0());
                    		
                    pushFollow(FOLLOW_2);
                    this_StateCondition_0=ruleStateCondition();

                    state._fsp--;


                    			current = this_StateCondition_0;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 2 :
                    // InternalBdd.g:870:3: this_PropCondition_1= rulePropCondition
                    {

                    			newCompositeNode(grammarAccess.getConditionAccess().getPropConditionParserRuleCall_1());
                    		
                    pushFollow(FOLLOW_2);
                    this_PropCondition_1=rulePropCondition();

                    state._fsp--;


                    			current = this_PropCondition_1;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 3 :
                    // InternalBdd.g:879:3: this_BetweenCondition_2= ruleBetweenCondition
                    {

                    			newCompositeNode(grammarAccess.getConditionAccess().getBetweenConditionParserRuleCall_2());
                    		
                    pushFollow(FOLLOW_2);
                    this_BetweenCondition_2=ruleBetweenCondition();

                    state._fsp--;


                    			current = this_BetweenCondition_2;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleCondition"


    // $ANTLR start "entryRuleStateCondition"
    // InternalBdd.g:891:1: entryRuleStateCondition returns [EObject current=null] : iv_ruleStateCondition= ruleStateCondition EOF ;
    public final EObject entryRuleStateCondition() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleStateCondition = null;


        try {
            // InternalBdd.g:891:55: (iv_ruleStateCondition= ruleStateCondition EOF )
            // InternalBdd.g:892:2: iv_ruleStateCondition= ruleStateCondition EOF
            {
             newCompositeNode(grammarAccess.getStateConditionRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleStateCondition=ruleStateCondition();

            state._fsp--;

             current =iv_ruleStateCondition; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleStateCondition"


    // $ANTLR start "ruleStateCondition"
    // InternalBdd.g:898:1: ruleStateCondition returns [EObject current=null] : ( ( (otherlv_0= RULE_ID ) ) otherlv_1= 'is' ( (lv_negated_2_0= 'not' ) )? (otherlv_3= 'in' )? ( (otherlv_4= RULE_ID ) ) ) ;
    public final EObject ruleStateCondition() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token lv_negated_2_0=null;
        Token otherlv_3=null;
        Token otherlv_4=null;


        	enterRule();

        try {
            // InternalBdd.g:904:2: ( ( ( (otherlv_0= RULE_ID ) ) otherlv_1= 'is' ( (lv_negated_2_0= 'not' ) )? (otherlv_3= 'in' )? ( (otherlv_4= RULE_ID ) ) ) )
            // InternalBdd.g:905:2: ( ( (otherlv_0= RULE_ID ) ) otherlv_1= 'is' ( (lv_negated_2_0= 'not' ) )? (otherlv_3= 'in' )? ( (otherlv_4= RULE_ID ) ) )
            {
            // InternalBdd.g:905:2: ( ( (otherlv_0= RULE_ID ) ) otherlv_1= 'is' ( (lv_negated_2_0= 'not' ) )? (otherlv_3= 'in' )? ( (otherlv_4= RULE_ID ) ) )
            // InternalBdd.g:906:3: ( (otherlv_0= RULE_ID ) ) otherlv_1= 'is' ( (lv_negated_2_0= 'not' ) )? (otherlv_3= 'in' )? ( (otherlv_4= RULE_ID ) )
            {
            // InternalBdd.g:906:3: ( (otherlv_0= RULE_ID ) )
            // InternalBdd.g:907:4: (otherlv_0= RULE_ID )
            {
            // InternalBdd.g:907:4: (otherlv_0= RULE_ID )
            // InternalBdd.g:908:5: otherlv_0= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getStateConditionRule());
            					}
            				
            otherlv_0=(Token)match(input,RULE_ID,FOLLOW_24); 

            					newLeafNode(otherlv_0, grammarAccess.getStateConditionAccess().getEntityEntityCrossReference_0_0());
            				

            }


            }

            otherlv_1=(Token)match(input,27,FOLLOW_25); 

            			newLeafNode(otherlv_1, grammarAccess.getStateConditionAccess().getIsKeyword_1());
            		
            // InternalBdd.g:923:3: ( (lv_negated_2_0= 'not' ) )?
            int alt12=2;
            int LA12_0 = input.LA(1);

            if ( (LA12_0==28) ) {
                alt12=1;
            }
            switch (alt12) {
                case 1 :
                    // InternalBdd.g:924:4: (lv_negated_2_0= 'not' )
                    {
                    // InternalBdd.g:924:4: (lv_negated_2_0= 'not' )
                    // InternalBdd.g:925:5: lv_negated_2_0= 'not'
                    {
                    lv_negated_2_0=(Token)match(input,28,FOLLOW_26); 

                    					newLeafNode(lv_negated_2_0, grammarAccess.getStateConditionAccess().getNegatedNotKeyword_2_0());
                    				

                    					if (current==null) {
                    						current = createModelElement(grammarAccess.getStateConditionRule());
                    					}
                    					setWithLastConsumed(current, "negated", lv_negated_2_0 != null, "not");
                    				

                    }


                    }
                    break;

            }

            // InternalBdd.g:937:3: (otherlv_3= 'in' )?
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( (LA13_0==29) ) {
                alt13=1;
            }
            switch (alt13) {
                case 1 :
                    // InternalBdd.g:938:4: otherlv_3= 'in'
                    {
                    otherlv_3=(Token)match(input,29,FOLLOW_3); 

                    				newLeafNode(otherlv_3, grammarAccess.getStateConditionAccess().getInKeyword_3());
                    			

                    }
                    break;

            }

            // InternalBdd.g:943:3: ( (otherlv_4= RULE_ID ) )
            // InternalBdd.g:944:4: (otherlv_4= RULE_ID )
            {
            // InternalBdd.g:944:4: (otherlv_4= RULE_ID )
            // InternalBdd.g:945:5: otherlv_4= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getStateConditionRule());
            					}
            				
            otherlv_4=(Token)match(input,RULE_ID,FOLLOW_2); 

            					newLeafNode(otherlv_4, grammarAccess.getStateConditionAccess().getStateStateCrossReference_4_0());
            				

            }


            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleStateCondition"


    // $ANTLR start "entryRulePropCondition"
    // InternalBdd.g:960:1: entryRulePropCondition returns [EObject current=null] : iv_rulePropCondition= rulePropCondition EOF ;
    public final EObject entryRulePropCondition() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePropCondition = null;


        try {
            // InternalBdd.g:960:54: (iv_rulePropCondition= rulePropCondition EOF )
            // InternalBdd.g:961:2: iv_rulePropCondition= rulePropCondition EOF
            {
             newCompositeNode(grammarAccess.getPropConditionRule()); 
            pushFollow(FOLLOW_1);
            iv_rulePropCondition=rulePropCondition();

            state._fsp--;

             current =iv_rulePropCondition; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulePropCondition"


    // $ANTLR start "rulePropCondition"
    // InternalBdd.g:967:1: rulePropCondition returns [EObject current=null] : ( ( (lv_ref_0_0= rulePropRef ) ) ( (lv_comp_1_0= ruleComp ) ) ( (lv_value_2_0= ruleValue ) ) ) ;
    public final EObject rulePropCondition() throws RecognitionException {
        EObject current = null;

        EObject lv_ref_0_0 = null;

        Enumerator lv_comp_1_0 = null;

        EObject lv_value_2_0 = null;



        	enterRule();

        try {
            // InternalBdd.g:973:2: ( ( ( (lv_ref_0_0= rulePropRef ) ) ( (lv_comp_1_0= ruleComp ) ) ( (lv_value_2_0= ruleValue ) ) ) )
            // InternalBdd.g:974:2: ( ( (lv_ref_0_0= rulePropRef ) ) ( (lv_comp_1_0= ruleComp ) ) ( (lv_value_2_0= ruleValue ) ) )
            {
            // InternalBdd.g:974:2: ( ( (lv_ref_0_0= rulePropRef ) ) ( (lv_comp_1_0= ruleComp ) ) ( (lv_value_2_0= ruleValue ) ) )
            // InternalBdd.g:975:3: ( (lv_ref_0_0= rulePropRef ) ) ( (lv_comp_1_0= ruleComp ) ) ( (lv_value_2_0= ruleValue ) )
            {
            // InternalBdd.g:975:3: ( (lv_ref_0_0= rulePropRef ) )
            // InternalBdd.g:976:4: (lv_ref_0_0= rulePropRef )
            {
            // InternalBdd.g:976:4: (lv_ref_0_0= rulePropRef )
            // InternalBdd.g:977:5: lv_ref_0_0= rulePropRef
            {

            					newCompositeNode(grammarAccess.getPropConditionAccess().getRefPropRefParserRuleCall_0_0());
            				
            pushFollow(FOLLOW_27);
            lv_ref_0_0=rulePropRef();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getPropConditionRule());
            					}
            					set(
            						current,
            						"ref",
            						lv_ref_0_0,
            						"dk.sdu.Bdd.PropRef");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalBdd.g:994:3: ( (lv_comp_1_0= ruleComp ) )
            // InternalBdd.g:995:4: (lv_comp_1_0= ruleComp )
            {
            // InternalBdd.g:995:4: (lv_comp_1_0= ruleComp )
            // InternalBdd.g:996:5: lv_comp_1_0= ruleComp
            {

            					newCompositeNode(grammarAccess.getPropConditionAccess().getCompCompEnumRuleCall_1_0());
            				
            pushFollow(FOLLOW_18);
            lv_comp_1_0=ruleComp();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getPropConditionRule());
            					}
            					set(
            						current,
            						"comp",
            						lv_comp_1_0,
            						"dk.sdu.Bdd.Comp");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalBdd.g:1013:3: ( (lv_value_2_0= ruleValue ) )
            // InternalBdd.g:1014:4: (lv_value_2_0= ruleValue )
            {
            // InternalBdd.g:1014:4: (lv_value_2_0= ruleValue )
            // InternalBdd.g:1015:5: lv_value_2_0= ruleValue
            {

            					newCompositeNode(grammarAccess.getPropConditionAccess().getValueValueParserRuleCall_2_0());
            				
            pushFollow(FOLLOW_2);
            lv_value_2_0=ruleValue();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getPropConditionRule());
            					}
            					set(
            						current,
            						"value",
            						lv_value_2_0,
            						"dk.sdu.Bdd.Value");
            					afterParserOrEnumRuleCall();
            				

            }


            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rulePropCondition"


    // $ANTLR start "entryRuleBetweenCondition"
    // InternalBdd.g:1036:1: entryRuleBetweenCondition returns [EObject current=null] : iv_ruleBetweenCondition= ruleBetweenCondition EOF ;
    public final EObject entryRuleBetweenCondition() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleBetweenCondition = null;


        try {
            // InternalBdd.g:1036:57: (iv_ruleBetweenCondition= ruleBetweenCondition EOF )
            // InternalBdd.g:1037:2: iv_ruleBetweenCondition= ruleBetweenCondition EOF
            {
             newCompositeNode(grammarAccess.getBetweenConditionRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleBetweenCondition=ruleBetweenCondition();

            state._fsp--;

             current =iv_ruleBetweenCondition; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleBetweenCondition"


    // $ANTLR start "ruleBetweenCondition"
    // InternalBdd.g:1043:1: ruleBetweenCondition returns [EObject current=null] : ( ( (lv_ref_0_0= rulePropRef ) ) otherlv_1= 'is' otherlv_2= 'between' ( (lv_low_3_0= ruleValue ) ) otherlv_4= ',' ( (lv_high_5_0= ruleValue ) ) ) ;
    public final EObject ruleBetweenCondition() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        EObject lv_ref_0_0 = null;

        EObject lv_low_3_0 = null;

        EObject lv_high_5_0 = null;



        	enterRule();

        try {
            // InternalBdd.g:1049:2: ( ( ( (lv_ref_0_0= rulePropRef ) ) otherlv_1= 'is' otherlv_2= 'between' ( (lv_low_3_0= ruleValue ) ) otherlv_4= ',' ( (lv_high_5_0= ruleValue ) ) ) )
            // InternalBdd.g:1050:2: ( ( (lv_ref_0_0= rulePropRef ) ) otherlv_1= 'is' otherlv_2= 'between' ( (lv_low_3_0= ruleValue ) ) otherlv_4= ',' ( (lv_high_5_0= ruleValue ) ) )
            {
            // InternalBdd.g:1050:2: ( ( (lv_ref_0_0= rulePropRef ) ) otherlv_1= 'is' otherlv_2= 'between' ( (lv_low_3_0= ruleValue ) ) otherlv_4= ',' ( (lv_high_5_0= ruleValue ) ) )
            // InternalBdd.g:1051:3: ( (lv_ref_0_0= rulePropRef ) ) otherlv_1= 'is' otherlv_2= 'between' ( (lv_low_3_0= ruleValue ) ) otherlv_4= ',' ( (lv_high_5_0= ruleValue ) )
            {
            // InternalBdd.g:1051:3: ( (lv_ref_0_0= rulePropRef ) )
            // InternalBdd.g:1052:4: (lv_ref_0_0= rulePropRef )
            {
            // InternalBdd.g:1052:4: (lv_ref_0_0= rulePropRef )
            // InternalBdd.g:1053:5: lv_ref_0_0= rulePropRef
            {

            					newCompositeNode(grammarAccess.getBetweenConditionAccess().getRefPropRefParserRuleCall_0_0());
            				
            pushFollow(FOLLOW_24);
            lv_ref_0_0=rulePropRef();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getBetweenConditionRule());
            					}
            					set(
            						current,
            						"ref",
            						lv_ref_0_0,
            						"dk.sdu.Bdd.PropRef");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_1=(Token)match(input,27,FOLLOW_28); 

            			newLeafNode(otherlv_1, grammarAccess.getBetweenConditionAccess().getIsKeyword_1());
            		
            otherlv_2=(Token)match(input,30,FOLLOW_18); 

            			newLeafNode(otherlv_2, grammarAccess.getBetweenConditionAccess().getBetweenKeyword_2());
            		
            // InternalBdd.g:1078:3: ( (lv_low_3_0= ruleValue ) )
            // InternalBdd.g:1079:4: (lv_low_3_0= ruleValue )
            {
            // InternalBdd.g:1079:4: (lv_low_3_0= ruleValue )
            // InternalBdd.g:1080:5: lv_low_3_0= ruleValue
            {

            					newCompositeNode(grammarAccess.getBetweenConditionAccess().getLowValueParserRuleCall_3_0());
            				
            pushFollow(FOLLOW_29);
            lv_low_3_0=ruleValue();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getBetweenConditionRule());
            					}
            					set(
            						current,
            						"low",
            						lv_low_3_0,
            						"dk.sdu.Bdd.Value");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_4=(Token)match(input,31,FOLLOW_18); 

            			newLeafNode(otherlv_4, grammarAccess.getBetweenConditionAccess().getCommaKeyword_4());
            		
            // InternalBdd.g:1101:3: ( (lv_high_5_0= ruleValue ) )
            // InternalBdd.g:1102:4: (lv_high_5_0= ruleValue )
            {
            // InternalBdd.g:1102:4: (lv_high_5_0= ruleValue )
            // InternalBdd.g:1103:5: lv_high_5_0= ruleValue
            {

            					newCompositeNode(grammarAccess.getBetweenConditionAccess().getHighValueParserRuleCall_5_0());
            				
            pushFollow(FOLLOW_2);
            lv_high_5_0=ruleValue();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getBetweenConditionRule());
            					}
            					set(
            						current,
            						"high",
            						lv_high_5_0,
            						"dk.sdu.Bdd.Value");
            					afterParserOrEnumRuleCall();
            				

            }


            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleBetweenCondition"


    // $ANTLR start "entryRuleEffect"
    // InternalBdd.g:1124:1: entryRuleEffect returns [EObject current=null] : iv_ruleEffect= ruleEffect EOF ;
    public final EObject entryRuleEffect() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleEffect = null;


        try {
            // InternalBdd.g:1124:47: (iv_ruleEffect= ruleEffect EOF )
            // InternalBdd.g:1125:2: iv_ruleEffect= ruleEffect EOF
            {
             newCompositeNode(grammarAccess.getEffectRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleEffect=ruleEffect();

            state._fsp--;

             current =iv_ruleEffect; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleEffect"


    // $ANTLR start "ruleEffect"
    // InternalBdd.g:1131:1: ruleEffect returns [EObject current=null] : (this_StateEffect_0= ruleStateEffect | this_AssignEffect_1= ruleAssignEffect | this_DeltaEffect_2= ruleDeltaEffect | this_RandomEffect_3= ruleRandomEffect ) ;
    public final EObject ruleEffect() throws RecognitionException {
        EObject current = null;

        EObject this_StateEffect_0 = null;

        EObject this_AssignEffect_1 = null;

        EObject this_DeltaEffect_2 = null;

        EObject this_RandomEffect_3 = null;



        	enterRule();

        try {
            // InternalBdd.g:1137:2: ( (this_StateEffect_0= ruleStateEffect | this_AssignEffect_1= ruleAssignEffect | this_DeltaEffect_2= ruleDeltaEffect | this_RandomEffect_3= ruleRandomEffect ) )
            // InternalBdd.g:1138:2: (this_StateEffect_0= ruleStateEffect | this_AssignEffect_1= ruleAssignEffect | this_DeltaEffect_2= ruleDeltaEffect | this_RandomEffect_3= ruleRandomEffect )
            {
            // InternalBdd.g:1138:2: (this_StateEffect_0= ruleStateEffect | this_AssignEffect_1= ruleAssignEffect | this_DeltaEffect_2= ruleDeltaEffect | this_RandomEffect_3= ruleRandomEffect )
            int alt14=4;
            int LA14_0 = input.LA(1);

            if ( (LA14_0==RULE_ID) ) {
                int LA14_1 = input.LA(2);

                if ( (LA14_1==33) ) {
                    int LA14_2 = input.LA(3);

                    if ( (LA14_2==RULE_ID) ) {
                        int LA14_4 = input.LA(4);

                        if ( (LA14_4==27) ) {
                            switch ( input.LA(5) ) {
                            case 53:
                            case 54:
                                {
                                alt14=3;
                                }
                                break;
                            case 30:
                                {
                                alt14=4;
                                }
                                break;
                            case RULE_ID:
                            case RULE_INT:
                                {
                                alt14=2;
                                }
                                break;
                            default:
                                NoViableAltException nvae =
                                    new NoViableAltException("", 14, 5, input);

                                throw nvae;
                            }

                        }
                        else {
                            NoViableAltException nvae =
                                new NoViableAltException("", 14, 4, input);

                            throw nvae;
                        }
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 14, 2, input);

                        throw nvae;
                    }
                }
                else if ( (LA14_1==27) ) {
                    alt14=1;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 14, 1, input);

                    throw nvae;
                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 14, 0, input);

                throw nvae;
            }
            switch (alt14) {
                case 1 :
                    // InternalBdd.g:1139:3: this_StateEffect_0= ruleStateEffect
                    {

                    			newCompositeNode(grammarAccess.getEffectAccess().getStateEffectParserRuleCall_0());
                    		
                    pushFollow(FOLLOW_2);
                    this_StateEffect_0=ruleStateEffect();

                    state._fsp--;


                    			current = this_StateEffect_0;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 2 :
                    // InternalBdd.g:1148:3: this_AssignEffect_1= ruleAssignEffect
                    {

                    			newCompositeNode(grammarAccess.getEffectAccess().getAssignEffectParserRuleCall_1());
                    		
                    pushFollow(FOLLOW_2);
                    this_AssignEffect_1=ruleAssignEffect();

                    state._fsp--;


                    			current = this_AssignEffect_1;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 3 :
                    // InternalBdd.g:1157:3: this_DeltaEffect_2= ruleDeltaEffect
                    {

                    			newCompositeNode(grammarAccess.getEffectAccess().getDeltaEffectParserRuleCall_2());
                    		
                    pushFollow(FOLLOW_2);
                    this_DeltaEffect_2=ruleDeltaEffect();

                    state._fsp--;


                    			current = this_DeltaEffect_2;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 4 :
                    // InternalBdd.g:1166:3: this_RandomEffect_3= ruleRandomEffect
                    {

                    			newCompositeNode(grammarAccess.getEffectAccess().getRandomEffectParserRuleCall_3());
                    		
                    pushFollow(FOLLOW_2);
                    this_RandomEffect_3=ruleRandomEffect();

                    state._fsp--;


                    			current = this_RandomEffect_3;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleEffect"


    // $ANTLR start "entryRuleStateEffect"
    // InternalBdd.g:1178:1: entryRuleStateEffect returns [EObject current=null] : iv_ruleStateEffect= ruleStateEffect EOF ;
    public final EObject entryRuleStateEffect() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleStateEffect = null;


        try {
            // InternalBdd.g:1178:52: (iv_ruleStateEffect= ruleStateEffect EOF )
            // InternalBdd.g:1179:2: iv_ruleStateEffect= ruleStateEffect EOF
            {
             newCompositeNode(grammarAccess.getStateEffectRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleStateEffect=ruleStateEffect();

            state._fsp--;

             current =iv_ruleStateEffect; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleStateEffect"


    // $ANTLR start "ruleStateEffect"
    // InternalBdd.g:1185:1: ruleStateEffect returns [EObject current=null] : ( ( (otherlv_0= RULE_ID ) ) otherlv_1= 'is' ( (otherlv_2= RULE_ID ) ) ) ;
    public final EObject ruleStateEffect() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;


        	enterRule();

        try {
            // InternalBdd.g:1191:2: ( ( ( (otherlv_0= RULE_ID ) ) otherlv_1= 'is' ( (otherlv_2= RULE_ID ) ) ) )
            // InternalBdd.g:1192:2: ( ( (otherlv_0= RULE_ID ) ) otherlv_1= 'is' ( (otherlv_2= RULE_ID ) ) )
            {
            // InternalBdd.g:1192:2: ( ( (otherlv_0= RULE_ID ) ) otherlv_1= 'is' ( (otherlv_2= RULE_ID ) ) )
            // InternalBdd.g:1193:3: ( (otherlv_0= RULE_ID ) ) otherlv_1= 'is' ( (otherlv_2= RULE_ID ) )
            {
            // InternalBdd.g:1193:3: ( (otherlv_0= RULE_ID ) )
            // InternalBdd.g:1194:4: (otherlv_0= RULE_ID )
            {
            // InternalBdd.g:1194:4: (otherlv_0= RULE_ID )
            // InternalBdd.g:1195:5: otherlv_0= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getStateEffectRule());
            					}
            				
            otherlv_0=(Token)match(input,RULE_ID,FOLLOW_24); 

            					newLeafNode(otherlv_0, grammarAccess.getStateEffectAccess().getEntityEntityCrossReference_0_0());
            				

            }


            }

            otherlv_1=(Token)match(input,27,FOLLOW_3); 

            			newLeafNode(otherlv_1, grammarAccess.getStateEffectAccess().getIsKeyword_1());
            		
            // InternalBdd.g:1210:3: ( (otherlv_2= RULE_ID ) )
            // InternalBdd.g:1211:4: (otherlv_2= RULE_ID )
            {
            // InternalBdd.g:1211:4: (otherlv_2= RULE_ID )
            // InternalBdd.g:1212:5: otherlv_2= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getStateEffectRule());
            					}
            				
            otherlv_2=(Token)match(input,RULE_ID,FOLLOW_2); 

            					newLeafNode(otherlv_2, grammarAccess.getStateEffectAccess().getStateStateCrossReference_2_0());
            				

            }


            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleStateEffect"


    // $ANTLR start "entryRuleAssignEffect"
    // InternalBdd.g:1227:1: entryRuleAssignEffect returns [EObject current=null] : iv_ruleAssignEffect= ruleAssignEffect EOF ;
    public final EObject entryRuleAssignEffect() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAssignEffect = null;


        try {
            // InternalBdd.g:1227:53: (iv_ruleAssignEffect= ruleAssignEffect EOF )
            // InternalBdd.g:1228:2: iv_ruleAssignEffect= ruleAssignEffect EOF
            {
             newCompositeNode(grammarAccess.getAssignEffectRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleAssignEffect=ruleAssignEffect();

            state._fsp--;

             current =iv_ruleAssignEffect; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleAssignEffect"


    // $ANTLR start "ruleAssignEffect"
    // InternalBdd.g:1234:1: ruleAssignEffect returns [EObject current=null] : ( ( (lv_ref_0_0= rulePropRef ) ) otherlv_1= 'is' ( (lv_value_2_0= ruleValue ) ) ) ;
    public final EObject ruleAssignEffect() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        EObject lv_ref_0_0 = null;

        EObject lv_value_2_0 = null;



        	enterRule();

        try {
            // InternalBdd.g:1240:2: ( ( ( (lv_ref_0_0= rulePropRef ) ) otherlv_1= 'is' ( (lv_value_2_0= ruleValue ) ) ) )
            // InternalBdd.g:1241:2: ( ( (lv_ref_0_0= rulePropRef ) ) otherlv_1= 'is' ( (lv_value_2_0= ruleValue ) ) )
            {
            // InternalBdd.g:1241:2: ( ( (lv_ref_0_0= rulePropRef ) ) otherlv_1= 'is' ( (lv_value_2_0= ruleValue ) ) )
            // InternalBdd.g:1242:3: ( (lv_ref_0_0= rulePropRef ) ) otherlv_1= 'is' ( (lv_value_2_0= ruleValue ) )
            {
            // InternalBdd.g:1242:3: ( (lv_ref_0_0= rulePropRef ) )
            // InternalBdd.g:1243:4: (lv_ref_0_0= rulePropRef )
            {
            // InternalBdd.g:1243:4: (lv_ref_0_0= rulePropRef )
            // InternalBdd.g:1244:5: lv_ref_0_0= rulePropRef
            {

            					newCompositeNode(grammarAccess.getAssignEffectAccess().getRefPropRefParserRuleCall_0_0());
            				
            pushFollow(FOLLOW_24);
            lv_ref_0_0=rulePropRef();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getAssignEffectRule());
            					}
            					set(
            						current,
            						"ref",
            						lv_ref_0_0,
            						"dk.sdu.Bdd.PropRef");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_1=(Token)match(input,27,FOLLOW_18); 

            			newLeafNode(otherlv_1, grammarAccess.getAssignEffectAccess().getIsKeyword_1());
            		
            // InternalBdd.g:1265:3: ( (lv_value_2_0= ruleValue ) )
            // InternalBdd.g:1266:4: (lv_value_2_0= ruleValue )
            {
            // InternalBdd.g:1266:4: (lv_value_2_0= ruleValue )
            // InternalBdd.g:1267:5: lv_value_2_0= ruleValue
            {

            					newCompositeNode(grammarAccess.getAssignEffectAccess().getValueValueParserRuleCall_2_0());
            				
            pushFollow(FOLLOW_2);
            lv_value_2_0=ruleValue();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getAssignEffectRule());
            					}
            					set(
            						current,
            						"value",
            						lv_value_2_0,
            						"dk.sdu.Bdd.Value");
            					afterParserOrEnumRuleCall();
            				

            }


            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleAssignEffect"


    // $ANTLR start "entryRuleDeltaEffect"
    // InternalBdd.g:1288:1: entryRuleDeltaEffect returns [EObject current=null] : iv_ruleDeltaEffect= ruleDeltaEffect EOF ;
    public final EObject entryRuleDeltaEffect() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleDeltaEffect = null;


        try {
            // InternalBdd.g:1288:52: (iv_ruleDeltaEffect= ruleDeltaEffect EOF )
            // InternalBdd.g:1289:2: iv_ruleDeltaEffect= ruleDeltaEffect EOF
            {
             newCompositeNode(grammarAccess.getDeltaEffectRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleDeltaEffect=ruleDeltaEffect();

            state._fsp--;

             current =iv_ruleDeltaEffect; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleDeltaEffect"


    // $ANTLR start "ruleDeltaEffect"
    // InternalBdd.g:1295:1: ruleDeltaEffect returns [EObject current=null] : ( ( (lv_ref_0_0= rulePropRef ) ) otherlv_1= 'is' ( (lv_op_2_0= ruleDeltaOp ) ) otherlv_3= 'by' ( (lv_value_4_0= ruleValue ) ) ) ;
    public final EObject ruleDeltaEffect() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_ref_0_0 = null;

        Enumerator lv_op_2_0 = null;

        EObject lv_value_4_0 = null;



        	enterRule();

        try {
            // InternalBdd.g:1301:2: ( ( ( (lv_ref_0_0= rulePropRef ) ) otherlv_1= 'is' ( (lv_op_2_0= ruleDeltaOp ) ) otherlv_3= 'by' ( (lv_value_4_0= ruleValue ) ) ) )
            // InternalBdd.g:1302:2: ( ( (lv_ref_0_0= rulePropRef ) ) otherlv_1= 'is' ( (lv_op_2_0= ruleDeltaOp ) ) otherlv_3= 'by' ( (lv_value_4_0= ruleValue ) ) )
            {
            // InternalBdd.g:1302:2: ( ( (lv_ref_0_0= rulePropRef ) ) otherlv_1= 'is' ( (lv_op_2_0= ruleDeltaOp ) ) otherlv_3= 'by' ( (lv_value_4_0= ruleValue ) ) )
            // InternalBdd.g:1303:3: ( (lv_ref_0_0= rulePropRef ) ) otherlv_1= 'is' ( (lv_op_2_0= ruleDeltaOp ) ) otherlv_3= 'by' ( (lv_value_4_0= ruleValue ) )
            {
            // InternalBdd.g:1303:3: ( (lv_ref_0_0= rulePropRef ) )
            // InternalBdd.g:1304:4: (lv_ref_0_0= rulePropRef )
            {
            // InternalBdd.g:1304:4: (lv_ref_0_0= rulePropRef )
            // InternalBdd.g:1305:5: lv_ref_0_0= rulePropRef
            {

            					newCompositeNode(grammarAccess.getDeltaEffectAccess().getRefPropRefParserRuleCall_0_0());
            				
            pushFollow(FOLLOW_24);
            lv_ref_0_0=rulePropRef();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getDeltaEffectRule());
            					}
            					set(
            						current,
            						"ref",
            						lv_ref_0_0,
            						"dk.sdu.Bdd.PropRef");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_1=(Token)match(input,27,FOLLOW_30); 

            			newLeafNode(otherlv_1, grammarAccess.getDeltaEffectAccess().getIsKeyword_1());
            		
            // InternalBdd.g:1326:3: ( (lv_op_2_0= ruleDeltaOp ) )
            // InternalBdd.g:1327:4: (lv_op_2_0= ruleDeltaOp )
            {
            // InternalBdd.g:1327:4: (lv_op_2_0= ruleDeltaOp )
            // InternalBdd.g:1328:5: lv_op_2_0= ruleDeltaOp
            {

            					newCompositeNode(grammarAccess.getDeltaEffectAccess().getOpDeltaOpEnumRuleCall_2_0());
            				
            pushFollow(FOLLOW_31);
            lv_op_2_0=ruleDeltaOp();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getDeltaEffectRule());
            					}
            					set(
            						current,
            						"op",
            						lv_op_2_0,
            						"dk.sdu.Bdd.DeltaOp");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_3=(Token)match(input,32,FOLLOW_18); 

            			newLeafNode(otherlv_3, grammarAccess.getDeltaEffectAccess().getByKeyword_3());
            		
            // InternalBdd.g:1349:3: ( (lv_value_4_0= ruleValue ) )
            // InternalBdd.g:1350:4: (lv_value_4_0= ruleValue )
            {
            // InternalBdd.g:1350:4: (lv_value_4_0= ruleValue )
            // InternalBdd.g:1351:5: lv_value_4_0= ruleValue
            {

            					newCompositeNode(grammarAccess.getDeltaEffectAccess().getValueValueParserRuleCall_4_0());
            				
            pushFollow(FOLLOW_2);
            lv_value_4_0=ruleValue();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getDeltaEffectRule());
            					}
            					set(
            						current,
            						"value",
            						lv_value_4_0,
            						"dk.sdu.Bdd.Value");
            					afterParserOrEnumRuleCall();
            				

            }


            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleDeltaEffect"


    // $ANTLR start "entryRuleRandomEffect"
    // InternalBdd.g:1372:1: entryRuleRandomEffect returns [EObject current=null] : iv_ruleRandomEffect= ruleRandomEffect EOF ;
    public final EObject entryRuleRandomEffect() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleRandomEffect = null;


        try {
            // InternalBdd.g:1372:53: (iv_ruleRandomEffect= ruleRandomEffect EOF )
            // InternalBdd.g:1373:2: iv_ruleRandomEffect= ruleRandomEffect EOF
            {
             newCompositeNode(grammarAccess.getRandomEffectRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleRandomEffect=ruleRandomEffect();

            state._fsp--;

             current =iv_ruleRandomEffect; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleRandomEffect"


    // $ANTLR start "ruleRandomEffect"
    // InternalBdd.g:1379:1: ruleRandomEffect returns [EObject current=null] : ( ( (lv_ref_0_0= rulePropRef ) ) otherlv_1= 'is' otherlv_2= 'between' ( (lv_low_3_0= ruleValue ) ) otherlv_4= ',' ( (lv_high_5_0= ruleValue ) ) ) ;
    public final EObject ruleRandomEffect() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        EObject lv_ref_0_0 = null;

        EObject lv_low_3_0 = null;

        EObject lv_high_5_0 = null;



        	enterRule();

        try {
            // InternalBdd.g:1385:2: ( ( ( (lv_ref_0_0= rulePropRef ) ) otherlv_1= 'is' otherlv_2= 'between' ( (lv_low_3_0= ruleValue ) ) otherlv_4= ',' ( (lv_high_5_0= ruleValue ) ) ) )
            // InternalBdd.g:1386:2: ( ( (lv_ref_0_0= rulePropRef ) ) otherlv_1= 'is' otherlv_2= 'between' ( (lv_low_3_0= ruleValue ) ) otherlv_4= ',' ( (lv_high_5_0= ruleValue ) ) )
            {
            // InternalBdd.g:1386:2: ( ( (lv_ref_0_0= rulePropRef ) ) otherlv_1= 'is' otherlv_2= 'between' ( (lv_low_3_0= ruleValue ) ) otherlv_4= ',' ( (lv_high_5_0= ruleValue ) ) )
            // InternalBdd.g:1387:3: ( (lv_ref_0_0= rulePropRef ) ) otherlv_1= 'is' otherlv_2= 'between' ( (lv_low_3_0= ruleValue ) ) otherlv_4= ',' ( (lv_high_5_0= ruleValue ) )
            {
            // InternalBdd.g:1387:3: ( (lv_ref_0_0= rulePropRef ) )
            // InternalBdd.g:1388:4: (lv_ref_0_0= rulePropRef )
            {
            // InternalBdd.g:1388:4: (lv_ref_0_0= rulePropRef )
            // InternalBdd.g:1389:5: lv_ref_0_0= rulePropRef
            {

            					newCompositeNode(grammarAccess.getRandomEffectAccess().getRefPropRefParserRuleCall_0_0());
            				
            pushFollow(FOLLOW_24);
            lv_ref_0_0=rulePropRef();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getRandomEffectRule());
            					}
            					set(
            						current,
            						"ref",
            						lv_ref_0_0,
            						"dk.sdu.Bdd.PropRef");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_1=(Token)match(input,27,FOLLOW_28); 

            			newLeafNode(otherlv_1, grammarAccess.getRandomEffectAccess().getIsKeyword_1());
            		
            otherlv_2=(Token)match(input,30,FOLLOW_18); 

            			newLeafNode(otherlv_2, grammarAccess.getRandomEffectAccess().getBetweenKeyword_2());
            		
            // InternalBdd.g:1414:3: ( (lv_low_3_0= ruleValue ) )
            // InternalBdd.g:1415:4: (lv_low_3_0= ruleValue )
            {
            // InternalBdd.g:1415:4: (lv_low_3_0= ruleValue )
            // InternalBdd.g:1416:5: lv_low_3_0= ruleValue
            {

            					newCompositeNode(grammarAccess.getRandomEffectAccess().getLowValueParserRuleCall_3_0());
            				
            pushFollow(FOLLOW_29);
            lv_low_3_0=ruleValue();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getRandomEffectRule());
            					}
            					set(
            						current,
            						"low",
            						lv_low_3_0,
            						"dk.sdu.Bdd.Value");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_4=(Token)match(input,31,FOLLOW_18); 

            			newLeafNode(otherlv_4, grammarAccess.getRandomEffectAccess().getCommaKeyword_4());
            		
            // InternalBdd.g:1437:3: ( (lv_high_5_0= ruleValue ) )
            // InternalBdd.g:1438:4: (lv_high_5_0= ruleValue )
            {
            // InternalBdd.g:1438:4: (lv_high_5_0= ruleValue )
            // InternalBdd.g:1439:5: lv_high_5_0= ruleValue
            {

            					newCompositeNode(grammarAccess.getRandomEffectAccess().getHighValueParserRuleCall_5_0());
            				
            pushFollow(FOLLOW_2);
            lv_high_5_0=ruleValue();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getRandomEffectRule());
            					}
            					set(
            						current,
            						"high",
            						lv_high_5_0,
            						"dk.sdu.Bdd.Value");
            					afterParserOrEnumRuleCall();
            				

            }


            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleRandomEffect"


    // $ANTLR start "entryRulePropRef"
    // InternalBdd.g:1460:1: entryRulePropRef returns [EObject current=null] : iv_rulePropRef= rulePropRef EOF ;
    public final EObject entryRulePropRef() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePropRef = null;


        try {
            // InternalBdd.g:1460:48: (iv_rulePropRef= rulePropRef EOF )
            // InternalBdd.g:1461:2: iv_rulePropRef= rulePropRef EOF
            {
             newCompositeNode(grammarAccess.getPropRefRule()); 
            pushFollow(FOLLOW_1);
            iv_rulePropRef=rulePropRef();

            state._fsp--;

             current =iv_rulePropRef; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulePropRef"


    // $ANTLR start "rulePropRef"
    // InternalBdd.g:1467:1: rulePropRef returns [EObject current=null] : ( ( (otherlv_0= RULE_ID ) ) otherlv_1= 's' ( (otherlv_2= RULE_ID ) ) ) ;
    public final EObject rulePropRef() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;


        	enterRule();

        try {
            // InternalBdd.g:1473:2: ( ( ( (otherlv_0= RULE_ID ) ) otherlv_1= 's' ( (otherlv_2= RULE_ID ) ) ) )
            // InternalBdd.g:1474:2: ( ( (otherlv_0= RULE_ID ) ) otherlv_1= 's' ( (otherlv_2= RULE_ID ) ) )
            {
            // InternalBdd.g:1474:2: ( ( (otherlv_0= RULE_ID ) ) otherlv_1= 's' ( (otherlv_2= RULE_ID ) ) )
            // InternalBdd.g:1475:3: ( (otherlv_0= RULE_ID ) ) otherlv_1= 's' ( (otherlv_2= RULE_ID ) )
            {
            // InternalBdd.g:1475:3: ( (otherlv_0= RULE_ID ) )
            // InternalBdd.g:1476:4: (otherlv_0= RULE_ID )
            {
            // InternalBdd.g:1476:4: (otherlv_0= RULE_ID )
            // InternalBdd.g:1477:5: otherlv_0= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getPropRefRule());
            					}
            				
            otherlv_0=(Token)match(input,RULE_ID,FOLLOW_32); 

            					newLeafNode(otherlv_0, grammarAccess.getPropRefAccess().getEntityEntityCrossReference_0_0());
            				

            }


            }

            otherlv_1=(Token)match(input,33,FOLLOW_3); 

            			newLeafNode(otherlv_1, grammarAccess.getPropRefAccess().getSKeyword_1());
            		
            // InternalBdd.g:1492:3: ( (otherlv_2= RULE_ID ) )
            // InternalBdd.g:1493:4: (otherlv_2= RULE_ID )
            {
            // InternalBdd.g:1493:4: (otherlv_2= RULE_ID )
            // InternalBdd.g:1494:5: otherlv_2= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getPropRefRule());
            					}
            				
            otherlv_2=(Token)match(input,RULE_ID,FOLLOW_2); 

            					newLeafNode(otherlv_2, grammarAccess.getPropRefAccess().getPropPropertyCrossReference_2_0());
            				

            }


            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rulePropRef"


    // $ANTLR start "entryRuleValue"
    // InternalBdd.g:1509:1: entryRuleValue returns [EObject current=null] : iv_ruleValue= ruleValue EOF ;
    public final EObject entryRuleValue() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleValue = null;


        try {
            // InternalBdd.g:1509:46: (iv_ruleValue= ruleValue EOF )
            // InternalBdd.g:1510:2: iv_ruleValue= ruleValue EOF
            {
             newCompositeNode(grammarAccess.getValueRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleValue=ruleValue();

            state._fsp--;

             current =iv_ruleValue; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleValue"


    // $ANTLR start "ruleValue"
    // InternalBdd.g:1516:1: ruleValue returns [EObject current=null] : (this_PropValue_0= rulePropValue | this_NumberValue_1= ruleNumberValue ) ;
    public final EObject ruleValue() throws RecognitionException {
        EObject current = null;

        EObject this_PropValue_0 = null;

        EObject this_NumberValue_1 = null;



        	enterRule();

        try {
            // InternalBdd.g:1522:2: ( (this_PropValue_0= rulePropValue | this_NumberValue_1= ruleNumberValue ) )
            // InternalBdd.g:1523:2: (this_PropValue_0= rulePropValue | this_NumberValue_1= ruleNumberValue )
            {
            // InternalBdd.g:1523:2: (this_PropValue_0= rulePropValue | this_NumberValue_1= ruleNumberValue )
            int alt15=2;
            int LA15_0 = input.LA(1);

            if ( (LA15_0==RULE_ID) ) {
                alt15=1;
            }
            else if ( (LA15_0==RULE_INT) ) {
                alt15=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 15, 0, input);

                throw nvae;
            }
            switch (alt15) {
                case 1 :
                    // InternalBdd.g:1524:3: this_PropValue_0= rulePropValue
                    {

                    			newCompositeNode(grammarAccess.getValueAccess().getPropValueParserRuleCall_0());
                    		
                    pushFollow(FOLLOW_2);
                    this_PropValue_0=rulePropValue();

                    state._fsp--;


                    			current = this_PropValue_0;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 2 :
                    // InternalBdd.g:1533:3: this_NumberValue_1= ruleNumberValue
                    {

                    			newCompositeNode(grammarAccess.getValueAccess().getNumberValueParserRuleCall_1());
                    		
                    pushFollow(FOLLOW_2);
                    this_NumberValue_1=ruleNumberValue();

                    state._fsp--;


                    			current = this_NumberValue_1;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleValue"


    // $ANTLR start "entryRulePropValue"
    // InternalBdd.g:1545:1: entryRulePropValue returns [EObject current=null] : iv_rulePropValue= rulePropValue EOF ;
    public final EObject entryRulePropValue() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePropValue = null;


        try {
            // InternalBdd.g:1545:50: (iv_rulePropValue= rulePropValue EOF )
            // InternalBdd.g:1546:2: iv_rulePropValue= rulePropValue EOF
            {
             newCompositeNode(grammarAccess.getPropValueRule()); 
            pushFollow(FOLLOW_1);
            iv_rulePropValue=rulePropValue();

            state._fsp--;

             current =iv_rulePropValue; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulePropValue"


    // $ANTLR start "rulePropValue"
    // InternalBdd.g:1552:1: rulePropValue returns [EObject current=null] : ( (lv_ref_0_0= rulePropRef ) ) ;
    public final EObject rulePropValue() throws RecognitionException {
        EObject current = null;

        EObject lv_ref_0_0 = null;



        	enterRule();

        try {
            // InternalBdd.g:1558:2: ( ( (lv_ref_0_0= rulePropRef ) ) )
            // InternalBdd.g:1559:2: ( (lv_ref_0_0= rulePropRef ) )
            {
            // InternalBdd.g:1559:2: ( (lv_ref_0_0= rulePropRef ) )
            // InternalBdd.g:1560:3: (lv_ref_0_0= rulePropRef )
            {
            // InternalBdd.g:1560:3: (lv_ref_0_0= rulePropRef )
            // InternalBdd.g:1561:4: lv_ref_0_0= rulePropRef
            {

            				newCompositeNode(grammarAccess.getPropValueAccess().getRefPropRefParserRuleCall_0());
            			
            pushFollow(FOLLOW_2);
            lv_ref_0_0=rulePropRef();

            state._fsp--;


            				if (current==null) {
            					current = createModelElementForParent(grammarAccess.getPropValueRule());
            				}
            				set(
            					current,
            					"ref",
            					lv_ref_0_0,
            					"dk.sdu.Bdd.PropRef");
            				afterParserOrEnumRuleCall();
            			

            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rulePropValue"


    // $ANTLR start "entryRuleNumberValue"
    // InternalBdd.g:1581:1: entryRuleNumberValue returns [EObject current=null] : iv_ruleNumberValue= ruleNumberValue EOF ;
    public final EObject entryRuleNumberValue() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleNumberValue = null;


        try {
            // InternalBdd.g:1581:52: (iv_ruleNumberValue= ruleNumberValue EOF )
            // InternalBdd.g:1582:2: iv_ruleNumberValue= ruleNumberValue EOF
            {
             newCompositeNode(grammarAccess.getNumberValueRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleNumberValue=ruleNumberValue();

            state._fsp--;

             current =iv_ruleNumberValue; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleNumberValue"


    // $ANTLR start "ruleNumberValue"
    // InternalBdd.g:1588:1: ruleNumberValue returns [EObject current=null] : ( (lv_value_0_0= RULE_INT ) ) ;
    public final EObject ruleNumberValue() throws RecognitionException {
        EObject current = null;

        Token lv_value_0_0=null;


        	enterRule();

        try {
            // InternalBdd.g:1594:2: ( ( (lv_value_0_0= RULE_INT ) ) )
            // InternalBdd.g:1595:2: ( (lv_value_0_0= RULE_INT ) )
            {
            // InternalBdd.g:1595:2: ( (lv_value_0_0= RULE_INT ) )
            // InternalBdd.g:1596:3: (lv_value_0_0= RULE_INT )
            {
            // InternalBdd.g:1596:3: (lv_value_0_0= RULE_INT )
            // InternalBdd.g:1597:4: lv_value_0_0= RULE_INT
            {
            lv_value_0_0=(Token)match(input,RULE_INT,FOLLOW_2); 

            				newLeafNode(lv_value_0_0, grammarAccess.getNumberValueAccess().getValueINTTerminalRuleCall_0());
            			

            				if (current==null) {
            					current = createModelElement(grammarAccess.getNumberValueRule());
            				}
            				setWithLastConsumed(
            					current,
            					"value",
            					lv_value_0_0,
            					"org.eclipse.xtext.common.Terminals.INT");
            			

            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleNumberValue"


    // $ANTLR start "entryRuleRequirement"
    // InternalBdd.g:1616:1: entryRuleRequirement returns [EObject current=null] : iv_ruleRequirement= ruleRequirement EOF ;
    public final EObject entryRuleRequirement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleRequirement = null;


        try {
            // InternalBdd.g:1616:52: (iv_ruleRequirement= ruleRequirement EOF )
            // InternalBdd.g:1617:2: iv_ruleRequirement= ruleRequirement EOF
            {
             newCompositeNode(grammarAccess.getRequirementRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleRequirement=ruleRequirement();

            state._fsp--;

             current =iv_ruleRequirement; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleRequirement"


    // $ANTLR start "ruleRequirement"
    // InternalBdd.g:1623:1: ruleRequirement returns [EObject current=null] : (otherlv_0= 'requirement:' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= 'given' otherlv_3= 'the' otherlv_4= 'system' otherlv_5= 'is' otherlv_6= 'running' ( (lv_timeBound_7_0= ruleTimeBound ) )? otherlv_8= 'check' ( (lv_check_9_0= ruleCheck ) ) ) ;
    public final EObject ruleRequirement() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_4=null;
        Token otherlv_5=null;
        Token otherlv_6=null;
        Token otherlv_8=null;
        EObject lv_timeBound_7_0 = null;

        EObject lv_check_9_0 = null;



        	enterRule();

        try {
            // InternalBdd.g:1629:2: ( (otherlv_0= 'requirement:' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= 'given' otherlv_3= 'the' otherlv_4= 'system' otherlv_5= 'is' otherlv_6= 'running' ( (lv_timeBound_7_0= ruleTimeBound ) )? otherlv_8= 'check' ( (lv_check_9_0= ruleCheck ) ) ) )
            // InternalBdd.g:1630:2: (otherlv_0= 'requirement:' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= 'given' otherlv_3= 'the' otherlv_4= 'system' otherlv_5= 'is' otherlv_6= 'running' ( (lv_timeBound_7_0= ruleTimeBound ) )? otherlv_8= 'check' ( (lv_check_9_0= ruleCheck ) ) )
            {
            // InternalBdd.g:1630:2: (otherlv_0= 'requirement:' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= 'given' otherlv_3= 'the' otherlv_4= 'system' otherlv_5= 'is' otherlv_6= 'running' ( (lv_timeBound_7_0= ruleTimeBound ) )? otherlv_8= 'check' ( (lv_check_9_0= ruleCheck ) ) )
            // InternalBdd.g:1631:3: otherlv_0= 'requirement:' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= 'given' otherlv_3= 'the' otherlv_4= 'system' otherlv_5= 'is' otherlv_6= 'running' ( (lv_timeBound_7_0= ruleTimeBound ) )? otherlv_8= 'check' ( (lv_check_9_0= ruleCheck ) )
            {
            otherlv_0=(Token)match(input,34,FOLLOW_3); 

            			newLeafNode(otherlv_0, grammarAccess.getRequirementAccess().getRequirementKeyword_0());
            		
            // InternalBdd.g:1635:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalBdd.g:1636:4: (lv_name_1_0= RULE_ID )
            {
            // InternalBdd.g:1636:4: (lv_name_1_0= RULE_ID )
            // InternalBdd.g:1637:5: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_14); 

            					newLeafNode(lv_name_1_0, grammarAccess.getRequirementAccess().getNameIDTerminalRuleCall_1_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getRequirementRule());
            					}
            					setWithLastConsumed(
            						current,
            						"name",
            						lv_name_1_0,
            						"org.eclipse.xtext.common.Terminals.ID");
            				

            }


            }

            otherlv_2=(Token)match(input,19,FOLLOW_33); 

            			newLeafNode(otherlv_2, grammarAccess.getRequirementAccess().getGivenKeyword_2());
            		
            otherlv_3=(Token)match(input,35,FOLLOW_34); 

            			newLeafNode(otherlv_3, grammarAccess.getRequirementAccess().getTheKeyword_3());
            		
            otherlv_4=(Token)match(input,36,FOLLOW_24); 

            			newLeafNode(otherlv_4, grammarAccess.getRequirementAccess().getSystemKeyword_4());
            		
            otherlv_5=(Token)match(input,27,FOLLOW_35); 

            			newLeafNode(otherlv_5, grammarAccess.getRequirementAccess().getIsKeyword_5());
            		
            otherlv_6=(Token)match(input,37,FOLLOW_36); 

            			newLeafNode(otherlv_6, grammarAccess.getRequirementAccess().getRunningKeyword_6());
            		
            // InternalBdd.g:1673:3: ( (lv_timeBound_7_0= ruleTimeBound ) )?
            int alt16=2;
            int LA16_0 = input.LA(1);

            if ( (LA16_0==39) ) {
                alt16=1;
            }
            switch (alt16) {
                case 1 :
                    // InternalBdd.g:1674:4: (lv_timeBound_7_0= ruleTimeBound )
                    {
                    // InternalBdd.g:1674:4: (lv_timeBound_7_0= ruleTimeBound )
                    // InternalBdd.g:1675:5: lv_timeBound_7_0= ruleTimeBound
                    {

                    					newCompositeNode(grammarAccess.getRequirementAccess().getTimeBoundTimeBoundParserRuleCall_7_0());
                    				
                    pushFollow(FOLLOW_37);
                    lv_timeBound_7_0=ruleTimeBound();

                    state._fsp--;


                    					if (current==null) {
                    						current = createModelElementForParent(grammarAccess.getRequirementRule());
                    					}
                    					set(
                    						current,
                    						"timeBound",
                    						lv_timeBound_7_0,
                    						"dk.sdu.Bdd.TimeBound");
                    					afterParserOrEnumRuleCall();
                    				

                    }


                    }
                    break;

            }

            otherlv_8=(Token)match(input,38,FOLLOW_38); 

            			newLeafNode(otherlv_8, grammarAccess.getRequirementAccess().getCheckKeyword_8());
            		
            // InternalBdd.g:1696:3: ( (lv_check_9_0= ruleCheck ) )
            // InternalBdd.g:1697:4: (lv_check_9_0= ruleCheck )
            {
            // InternalBdd.g:1697:4: (lv_check_9_0= ruleCheck )
            // InternalBdd.g:1698:5: lv_check_9_0= ruleCheck
            {

            					newCompositeNode(grammarAccess.getRequirementAccess().getCheckCheckParserRuleCall_9_0());
            				
            pushFollow(FOLLOW_2);
            lv_check_9_0=ruleCheck();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getRequirementRule());
            					}
            					set(
            						current,
            						"check",
            						lv_check_9_0,
            						"dk.sdu.Bdd.Check");
            					afterParserOrEnumRuleCall();
            				

            }


            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleRequirement"


    // $ANTLR start "entryRuleTimeBound"
    // InternalBdd.g:1719:1: entryRuleTimeBound returns [EObject current=null] : iv_ruleTimeBound= ruleTimeBound EOF ;
    public final EObject entryRuleTimeBound() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTimeBound = null;


        try {
            // InternalBdd.g:1719:50: (iv_ruleTimeBound= ruleTimeBound EOF )
            // InternalBdd.g:1720:2: iv_ruleTimeBound= ruleTimeBound EOF
            {
             newCompositeNode(grammarAccess.getTimeBoundRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleTimeBound=ruleTimeBound();

            state._fsp--;

             current =iv_ruleTimeBound; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleTimeBound"


    // $ANTLR start "ruleTimeBound"
    // InternalBdd.g:1726:1: ruleTimeBound returns [EObject current=null] : (otherlv_0= 'within' ( (lv_amount_1_0= RULE_INT ) ) ( (lv_unit_2_0= ruleTimeUnit ) ) ) ;
    public final EObject ruleTimeBound() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_amount_1_0=null;
        Enumerator lv_unit_2_0 = null;



        	enterRule();

        try {
            // InternalBdd.g:1732:2: ( (otherlv_0= 'within' ( (lv_amount_1_0= RULE_INT ) ) ( (lv_unit_2_0= ruleTimeUnit ) ) ) )
            // InternalBdd.g:1733:2: (otherlv_0= 'within' ( (lv_amount_1_0= RULE_INT ) ) ( (lv_unit_2_0= ruleTimeUnit ) ) )
            {
            // InternalBdd.g:1733:2: (otherlv_0= 'within' ( (lv_amount_1_0= RULE_INT ) ) ( (lv_unit_2_0= ruleTimeUnit ) ) )
            // InternalBdd.g:1734:3: otherlv_0= 'within' ( (lv_amount_1_0= RULE_INT ) ) ( (lv_unit_2_0= ruleTimeUnit ) )
            {
            otherlv_0=(Token)match(input,39,FOLLOW_13); 

            			newLeafNode(otherlv_0, grammarAccess.getTimeBoundAccess().getWithinKeyword_0());
            		
            // InternalBdd.g:1738:3: ( (lv_amount_1_0= RULE_INT ) )
            // InternalBdd.g:1739:4: (lv_amount_1_0= RULE_INT )
            {
            // InternalBdd.g:1739:4: (lv_amount_1_0= RULE_INT )
            // InternalBdd.g:1740:5: lv_amount_1_0= RULE_INT
            {
            lv_amount_1_0=(Token)match(input,RULE_INT,FOLLOW_20); 

            					newLeafNode(lv_amount_1_0, grammarAccess.getTimeBoundAccess().getAmountINTTerminalRuleCall_1_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getTimeBoundRule());
            					}
            					setWithLastConsumed(
            						current,
            						"amount",
            						lv_amount_1_0,
            						"org.eclipse.xtext.common.Terminals.INT");
            				

            }


            }

            // InternalBdd.g:1756:3: ( (lv_unit_2_0= ruleTimeUnit ) )
            // InternalBdd.g:1757:4: (lv_unit_2_0= ruleTimeUnit )
            {
            // InternalBdd.g:1757:4: (lv_unit_2_0= ruleTimeUnit )
            // InternalBdd.g:1758:5: lv_unit_2_0= ruleTimeUnit
            {

            					newCompositeNode(grammarAccess.getTimeBoundAccess().getUnitTimeUnitEnumRuleCall_2_0());
            				
            pushFollow(FOLLOW_2);
            lv_unit_2_0=ruleTimeUnit();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getTimeBoundRule());
            					}
            					set(
            						current,
            						"unit",
            						lv_unit_2_0,
            						"dk.sdu.Bdd.TimeUnit");
            					afterParserOrEnumRuleCall();
            				

            }


            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleTimeBound"


    // $ANTLR start "entryRuleCheck"
    // InternalBdd.g:1779:1: entryRuleCheck returns [EObject current=null] : iv_ruleCheck= ruleCheck EOF ;
    public final EObject entryRuleCheck() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleCheck = null;


        try {
            // InternalBdd.g:1779:46: (iv_ruleCheck= ruleCheck EOF )
            // InternalBdd.g:1780:2: iv_ruleCheck= ruleCheck EOF
            {
             newCompositeNode(grammarAccess.getCheckRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleCheck=ruleCheck();

            state._fsp--;

             current =iv_ruleCheck; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleCheck"


    // $ANTLR start "ruleCheck"
    // InternalBdd.g:1786:1: ruleCheck returns [EObject current=null] : (this_CheckQuery_0= ruleCheckQuery | this_NeverCheck_1= ruleNeverCheck | this_MaxCheck_2= ruleMaxCheck | this_MinCheck_3= ruleMinCheck ) ;
    public final EObject ruleCheck() throws RecognitionException {
        EObject current = null;

        EObject this_CheckQuery_0 = null;

        EObject this_NeverCheck_1 = null;

        EObject this_MaxCheck_2 = null;

        EObject this_MinCheck_3 = null;



        	enterRule();

        try {
            // InternalBdd.g:1792:2: ( (this_CheckQuery_0= ruleCheckQuery | this_NeverCheck_1= ruleNeverCheck | this_MaxCheck_2= ruleMaxCheck | this_MinCheck_3= ruleMinCheck ) )
            // InternalBdd.g:1793:2: (this_CheckQuery_0= ruleCheckQuery | this_NeverCheck_1= ruleNeverCheck | this_MaxCheck_2= ruleMaxCheck | this_MinCheck_3= ruleMinCheck )
            {
            // InternalBdd.g:1793:2: (this_CheckQuery_0= ruleCheckQuery | this_NeverCheck_1= ruleNeverCheck | this_MaxCheck_2= ruleMaxCheck | this_MinCheck_3= ruleMinCheck )
            int alt17=4;
            switch ( input.LA(1) ) {
            case 40:
                {
                alt17=1;
                }
                break;
            case 41:
                {
                int LA17_2 = input.LA(2);

                if ( ((LA17_2>=42 && LA17_2<=43)) ) {
                    alt17=1;
                }
                else if ( (LA17_2==45) ) {
                    alt17=2;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 17, 2, input);

                    throw nvae;
                }
                }
                break;
            case 35:
                {
                int LA17_3 = input.LA(2);

                if ( (LA17_3==46) ) {
                    alt17=3;
                }
                else if ( (LA17_3==48) ) {
                    alt17=4;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 17, 3, input);

                    throw nvae;
                }
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 17, 0, input);

                throw nvae;
            }

            switch (alt17) {
                case 1 :
                    // InternalBdd.g:1794:3: this_CheckQuery_0= ruleCheckQuery
                    {

                    			newCompositeNode(grammarAccess.getCheckAccess().getCheckQueryParserRuleCall_0());
                    		
                    pushFollow(FOLLOW_2);
                    this_CheckQuery_0=ruleCheckQuery();

                    state._fsp--;


                    			current = this_CheckQuery_0;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 2 :
                    // InternalBdd.g:1803:3: this_NeverCheck_1= ruleNeverCheck
                    {

                    			newCompositeNode(grammarAccess.getCheckAccess().getNeverCheckParserRuleCall_1());
                    		
                    pushFollow(FOLLOW_2);
                    this_NeverCheck_1=ruleNeverCheck();

                    state._fsp--;


                    			current = this_NeverCheck_1;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 3 :
                    // InternalBdd.g:1812:3: this_MaxCheck_2= ruleMaxCheck
                    {

                    			newCompositeNode(grammarAccess.getCheckAccess().getMaxCheckParserRuleCall_2());
                    		
                    pushFollow(FOLLOW_2);
                    this_MaxCheck_2=ruleMaxCheck();

                    state._fsp--;


                    			current = this_MaxCheck_2;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 4 :
                    // InternalBdd.g:1821:3: this_MinCheck_3= ruleMinCheck
                    {

                    			newCompositeNode(grammarAccess.getCheckAccess().getMinCheckParserRuleCall_3());
                    		
                    pushFollow(FOLLOW_2);
                    this_MinCheck_3=ruleMinCheck();

                    state._fsp--;


                    			current = this_MinCheck_3;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleCheck"


    // $ANTLR start "entryRuleCheckQuery"
    // InternalBdd.g:1833:1: entryRuleCheckQuery returns [EObject current=null] : iv_ruleCheckQuery= ruleCheckQuery EOF ;
    public final EObject entryRuleCheckQuery() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleCheckQuery = null;


        try {
            // InternalBdd.g:1833:51: (iv_ruleCheckQuery= ruleCheckQuery EOF )
            // InternalBdd.g:1834:2: iv_ruleCheckQuery= ruleCheckQuery EOF
            {
             newCompositeNode(grammarAccess.getCheckQueryRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleCheckQuery=ruleCheckQuery();

            state._fsp--;

             current =iv_ruleCheckQuery; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleCheckQuery"


    // $ANTLR start "ruleCheckQuery"
    // InternalBdd.g:1840:1: ruleCheckQuery returns [EObject current=null] : ( ( ( (lv_existential_0_0= 'can' ) ) | otherlv_1= 'should' ) ( ( (lv_diamond_2_0= 'eventually' ) ) | otherlv_3= 'always' ) otherlv_4= 'be' ( (lv_cond_5_0= ruleReqCond ) ) ) ;
    public final EObject ruleCheckQuery() throws RecognitionException {
        EObject current = null;

        Token lv_existential_0_0=null;
        Token otherlv_1=null;
        Token lv_diamond_2_0=null;
        Token otherlv_3=null;
        Token otherlv_4=null;
        EObject lv_cond_5_0 = null;



        	enterRule();

        try {
            // InternalBdd.g:1846:2: ( ( ( ( (lv_existential_0_0= 'can' ) ) | otherlv_1= 'should' ) ( ( (lv_diamond_2_0= 'eventually' ) ) | otherlv_3= 'always' ) otherlv_4= 'be' ( (lv_cond_5_0= ruleReqCond ) ) ) )
            // InternalBdd.g:1847:2: ( ( ( (lv_existential_0_0= 'can' ) ) | otherlv_1= 'should' ) ( ( (lv_diamond_2_0= 'eventually' ) ) | otherlv_3= 'always' ) otherlv_4= 'be' ( (lv_cond_5_0= ruleReqCond ) ) )
            {
            // InternalBdd.g:1847:2: ( ( ( (lv_existential_0_0= 'can' ) ) | otherlv_1= 'should' ) ( ( (lv_diamond_2_0= 'eventually' ) ) | otherlv_3= 'always' ) otherlv_4= 'be' ( (lv_cond_5_0= ruleReqCond ) ) )
            // InternalBdd.g:1848:3: ( ( (lv_existential_0_0= 'can' ) ) | otherlv_1= 'should' ) ( ( (lv_diamond_2_0= 'eventually' ) ) | otherlv_3= 'always' ) otherlv_4= 'be' ( (lv_cond_5_0= ruleReqCond ) )
            {
            // InternalBdd.g:1848:3: ( ( (lv_existential_0_0= 'can' ) ) | otherlv_1= 'should' )
            int alt18=2;
            int LA18_0 = input.LA(1);

            if ( (LA18_0==40) ) {
                alt18=1;
            }
            else if ( (LA18_0==41) ) {
                alt18=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 18, 0, input);

                throw nvae;
            }
            switch (alt18) {
                case 1 :
                    // InternalBdd.g:1849:4: ( (lv_existential_0_0= 'can' ) )
                    {
                    // InternalBdd.g:1849:4: ( (lv_existential_0_0= 'can' ) )
                    // InternalBdd.g:1850:5: (lv_existential_0_0= 'can' )
                    {
                    // InternalBdd.g:1850:5: (lv_existential_0_0= 'can' )
                    // InternalBdd.g:1851:6: lv_existential_0_0= 'can'
                    {
                    lv_existential_0_0=(Token)match(input,40,FOLLOW_39); 

                    						newLeafNode(lv_existential_0_0, grammarAccess.getCheckQueryAccess().getExistentialCanKeyword_0_0_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getCheckQueryRule());
                    						}
                    						setWithLastConsumed(current, "existential", lv_existential_0_0 != null, "can");
                    					

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalBdd.g:1864:4: otherlv_1= 'should'
                    {
                    otherlv_1=(Token)match(input,41,FOLLOW_39); 

                    				newLeafNode(otherlv_1, grammarAccess.getCheckQueryAccess().getShouldKeyword_0_1());
                    			

                    }
                    break;

            }

            // InternalBdd.g:1869:3: ( ( (lv_diamond_2_0= 'eventually' ) ) | otherlv_3= 'always' )
            int alt19=2;
            int LA19_0 = input.LA(1);

            if ( (LA19_0==42) ) {
                alt19=1;
            }
            else if ( (LA19_0==43) ) {
                alt19=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 19, 0, input);

                throw nvae;
            }
            switch (alt19) {
                case 1 :
                    // InternalBdd.g:1870:4: ( (lv_diamond_2_0= 'eventually' ) )
                    {
                    // InternalBdd.g:1870:4: ( (lv_diamond_2_0= 'eventually' ) )
                    // InternalBdd.g:1871:5: (lv_diamond_2_0= 'eventually' )
                    {
                    // InternalBdd.g:1871:5: (lv_diamond_2_0= 'eventually' )
                    // InternalBdd.g:1872:6: lv_diamond_2_0= 'eventually'
                    {
                    lv_diamond_2_0=(Token)match(input,42,FOLLOW_40); 

                    						newLeafNode(lv_diamond_2_0, grammarAccess.getCheckQueryAccess().getDiamondEventuallyKeyword_1_0_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getCheckQueryRule());
                    						}
                    						setWithLastConsumed(current, "diamond", lv_diamond_2_0 != null, "eventually");
                    					

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalBdd.g:1885:4: otherlv_3= 'always'
                    {
                    otherlv_3=(Token)match(input,43,FOLLOW_40); 

                    				newLeafNode(otherlv_3, grammarAccess.getCheckQueryAccess().getAlwaysKeyword_1_1());
                    			

                    }
                    break;

            }

            otherlv_4=(Token)match(input,44,FOLLOW_3); 

            			newLeafNode(otherlv_4, grammarAccess.getCheckQueryAccess().getBeKeyword_2());
            		
            // InternalBdd.g:1894:3: ( (lv_cond_5_0= ruleReqCond ) )
            // InternalBdd.g:1895:4: (lv_cond_5_0= ruleReqCond )
            {
            // InternalBdd.g:1895:4: (lv_cond_5_0= ruleReqCond )
            // InternalBdd.g:1896:5: lv_cond_5_0= ruleReqCond
            {

            					newCompositeNode(grammarAccess.getCheckQueryAccess().getCondReqCondParserRuleCall_3_0());
            				
            pushFollow(FOLLOW_2);
            lv_cond_5_0=ruleReqCond();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getCheckQueryRule());
            					}
            					set(
            						current,
            						"cond",
            						lv_cond_5_0,
            						"dk.sdu.Bdd.ReqCond");
            					afterParserOrEnumRuleCall();
            				

            }


            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleCheckQuery"


    // $ANTLR start "entryRuleNeverCheck"
    // InternalBdd.g:1917:1: entryRuleNeverCheck returns [EObject current=null] : iv_ruleNeverCheck= ruleNeverCheck EOF ;
    public final EObject entryRuleNeverCheck() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleNeverCheck = null;


        try {
            // InternalBdd.g:1917:51: (iv_ruleNeverCheck= ruleNeverCheck EOF )
            // InternalBdd.g:1918:2: iv_ruleNeverCheck= ruleNeverCheck EOF
            {
             newCompositeNode(grammarAccess.getNeverCheckRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleNeverCheck=ruleNeverCheck();

            state._fsp--;

             current =iv_ruleNeverCheck; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleNeverCheck"


    // $ANTLR start "ruleNeverCheck"
    // InternalBdd.g:1924:1: ruleNeverCheck returns [EObject current=null] : (otherlv_0= 'should' otherlv_1= 'never' otherlv_2= 'be' ( (lv_cond_3_0= ruleReqCond ) ) ) ;
    public final EObject ruleNeverCheck() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;
        EObject lv_cond_3_0 = null;



        	enterRule();

        try {
            // InternalBdd.g:1930:2: ( (otherlv_0= 'should' otherlv_1= 'never' otherlv_2= 'be' ( (lv_cond_3_0= ruleReqCond ) ) ) )
            // InternalBdd.g:1931:2: (otherlv_0= 'should' otherlv_1= 'never' otherlv_2= 'be' ( (lv_cond_3_0= ruleReqCond ) ) )
            {
            // InternalBdd.g:1931:2: (otherlv_0= 'should' otherlv_1= 'never' otherlv_2= 'be' ( (lv_cond_3_0= ruleReqCond ) ) )
            // InternalBdd.g:1932:3: otherlv_0= 'should' otherlv_1= 'never' otherlv_2= 'be' ( (lv_cond_3_0= ruleReqCond ) )
            {
            otherlv_0=(Token)match(input,41,FOLLOW_41); 

            			newLeafNode(otherlv_0, grammarAccess.getNeverCheckAccess().getShouldKeyword_0());
            		
            otherlv_1=(Token)match(input,45,FOLLOW_40); 

            			newLeafNode(otherlv_1, grammarAccess.getNeverCheckAccess().getNeverKeyword_1());
            		
            otherlv_2=(Token)match(input,44,FOLLOW_3); 

            			newLeafNode(otherlv_2, grammarAccess.getNeverCheckAccess().getBeKeyword_2());
            		
            // InternalBdd.g:1944:3: ( (lv_cond_3_0= ruleReqCond ) )
            // InternalBdd.g:1945:4: (lv_cond_3_0= ruleReqCond )
            {
            // InternalBdd.g:1945:4: (lv_cond_3_0= ruleReqCond )
            // InternalBdd.g:1946:5: lv_cond_3_0= ruleReqCond
            {

            					newCompositeNode(grammarAccess.getNeverCheckAccess().getCondReqCondParserRuleCall_3_0());
            				
            pushFollow(FOLLOW_2);
            lv_cond_3_0=ruleReqCond();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getNeverCheckRule());
            					}
            					set(
            						current,
            						"cond",
            						lv_cond_3_0,
            						"dk.sdu.Bdd.ReqCond");
            					afterParserOrEnumRuleCall();
            				

            }


            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleNeverCheck"


    // $ANTLR start "entryRuleMaxCheck"
    // InternalBdd.g:1967:1: entryRuleMaxCheck returns [EObject current=null] : iv_ruleMaxCheck= ruleMaxCheck EOF ;
    public final EObject entryRuleMaxCheck() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMaxCheck = null;


        try {
            // InternalBdd.g:1967:49: (iv_ruleMaxCheck= ruleMaxCheck EOF )
            // InternalBdd.g:1968:2: iv_ruleMaxCheck= ruleMaxCheck EOF
            {
             newCompositeNode(grammarAccess.getMaxCheckRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleMaxCheck=ruleMaxCheck();

            state._fsp--;

             current =iv_ruleMaxCheck; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleMaxCheck"


    // $ANTLR start "ruleMaxCheck"
    // InternalBdd.g:1974:1: ruleMaxCheck returns [EObject current=null] : (otherlv_0= 'the' otherlv_1= 'maximum' otherlv_2= 'of' ( (lv_ref_3_0= rulePropRef ) ) ) ;
    public final EObject ruleMaxCheck() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;
        EObject lv_ref_3_0 = null;



        	enterRule();

        try {
            // InternalBdd.g:1980:2: ( (otherlv_0= 'the' otherlv_1= 'maximum' otherlv_2= 'of' ( (lv_ref_3_0= rulePropRef ) ) ) )
            // InternalBdd.g:1981:2: (otherlv_0= 'the' otherlv_1= 'maximum' otherlv_2= 'of' ( (lv_ref_3_0= rulePropRef ) ) )
            {
            // InternalBdd.g:1981:2: (otherlv_0= 'the' otherlv_1= 'maximum' otherlv_2= 'of' ( (lv_ref_3_0= rulePropRef ) ) )
            // InternalBdd.g:1982:3: otherlv_0= 'the' otherlv_1= 'maximum' otherlv_2= 'of' ( (lv_ref_3_0= rulePropRef ) )
            {
            otherlv_0=(Token)match(input,35,FOLLOW_42); 

            			newLeafNode(otherlv_0, grammarAccess.getMaxCheckAccess().getTheKeyword_0());
            		
            otherlv_1=(Token)match(input,46,FOLLOW_43); 

            			newLeafNode(otherlv_1, grammarAccess.getMaxCheckAccess().getMaximumKeyword_1());
            		
            otherlv_2=(Token)match(input,47,FOLLOW_3); 

            			newLeafNode(otherlv_2, grammarAccess.getMaxCheckAccess().getOfKeyword_2());
            		
            // InternalBdd.g:1994:3: ( (lv_ref_3_0= rulePropRef ) )
            // InternalBdd.g:1995:4: (lv_ref_3_0= rulePropRef )
            {
            // InternalBdd.g:1995:4: (lv_ref_3_0= rulePropRef )
            // InternalBdd.g:1996:5: lv_ref_3_0= rulePropRef
            {

            					newCompositeNode(grammarAccess.getMaxCheckAccess().getRefPropRefParserRuleCall_3_0());
            				
            pushFollow(FOLLOW_2);
            lv_ref_3_0=rulePropRef();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getMaxCheckRule());
            					}
            					set(
            						current,
            						"ref",
            						lv_ref_3_0,
            						"dk.sdu.Bdd.PropRef");
            					afterParserOrEnumRuleCall();
            				

            }


            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleMaxCheck"


    // $ANTLR start "entryRuleMinCheck"
    // InternalBdd.g:2017:1: entryRuleMinCheck returns [EObject current=null] : iv_ruleMinCheck= ruleMinCheck EOF ;
    public final EObject entryRuleMinCheck() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMinCheck = null;


        try {
            // InternalBdd.g:2017:49: (iv_ruleMinCheck= ruleMinCheck EOF )
            // InternalBdd.g:2018:2: iv_ruleMinCheck= ruleMinCheck EOF
            {
             newCompositeNode(grammarAccess.getMinCheckRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleMinCheck=ruleMinCheck();

            state._fsp--;

             current =iv_ruleMinCheck; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleMinCheck"


    // $ANTLR start "ruleMinCheck"
    // InternalBdd.g:2024:1: ruleMinCheck returns [EObject current=null] : (otherlv_0= 'the' otherlv_1= 'minimum' otherlv_2= 'of' ( (lv_ref_3_0= rulePropRef ) ) ) ;
    public final EObject ruleMinCheck() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;
        EObject lv_ref_3_0 = null;



        	enterRule();

        try {
            // InternalBdd.g:2030:2: ( (otherlv_0= 'the' otherlv_1= 'minimum' otherlv_2= 'of' ( (lv_ref_3_0= rulePropRef ) ) ) )
            // InternalBdd.g:2031:2: (otherlv_0= 'the' otherlv_1= 'minimum' otherlv_2= 'of' ( (lv_ref_3_0= rulePropRef ) ) )
            {
            // InternalBdd.g:2031:2: (otherlv_0= 'the' otherlv_1= 'minimum' otherlv_2= 'of' ( (lv_ref_3_0= rulePropRef ) ) )
            // InternalBdd.g:2032:3: otherlv_0= 'the' otherlv_1= 'minimum' otherlv_2= 'of' ( (lv_ref_3_0= rulePropRef ) )
            {
            otherlv_0=(Token)match(input,35,FOLLOW_44); 

            			newLeafNode(otherlv_0, grammarAccess.getMinCheckAccess().getTheKeyword_0());
            		
            otherlv_1=(Token)match(input,48,FOLLOW_43); 

            			newLeafNode(otherlv_1, grammarAccess.getMinCheckAccess().getMinimumKeyword_1());
            		
            otherlv_2=(Token)match(input,47,FOLLOW_3); 

            			newLeafNode(otherlv_2, grammarAccess.getMinCheckAccess().getOfKeyword_2());
            		
            // InternalBdd.g:2044:3: ( (lv_ref_3_0= rulePropRef ) )
            // InternalBdd.g:2045:4: (lv_ref_3_0= rulePropRef )
            {
            // InternalBdd.g:2045:4: (lv_ref_3_0= rulePropRef )
            // InternalBdd.g:2046:5: lv_ref_3_0= rulePropRef
            {

            					newCompositeNode(grammarAccess.getMinCheckAccess().getRefPropRefParserRuleCall_3_0());
            				
            pushFollow(FOLLOW_2);
            lv_ref_3_0=rulePropRef();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getMinCheckRule());
            					}
            					set(
            						current,
            						"ref",
            						lv_ref_3_0,
            						"dk.sdu.Bdd.PropRef");
            					afterParserOrEnumRuleCall();
            				

            }


            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleMinCheck"


    // $ANTLR start "entryRuleReqCond"
    // InternalBdd.g:2067:1: entryRuleReqCond returns [EObject current=null] : iv_ruleReqCond= ruleReqCond EOF ;
    public final EObject entryRuleReqCond() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleReqCond = null;


        try {
            // InternalBdd.g:2067:48: (iv_ruleReqCond= ruleReqCond EOF )
            // InternalBdd.g:2068:2: iv_ruleReqCond= ruleReqCond EOF
            {
             newCompositeNode(grammarAccess.getReqCondRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleReqCond=ruleReqCond();

            state._fsp--;

             current =iv_ruleReqCond; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleReqCond"


    // $ANTLR start "ruleReqCond"
    // InternalBdd.g:2074:1: ruleReqCond returns [EObject current=null] : (this_StateCondition_0= ruleStateCondition | this_PropCondition_1= rulePropCondition | this_BetweenCondition_2= ruleBetweenCondition ) ;
    public final EObject ruleReqCond() throws RecognitionException {
        EObject current = null;

        EObject this_StateCondition_0 = null;

        EObject this_PropCondition_1 = null;

        EObject this_BetweenCondition_2 = null;



        	enterRule();

        try {
            // InternalBdd.g:2080:2: ( (this_StateCondition_0= ruleStateCondition | this_PropCondition_1= rulePropCondition | this_BetweenCondition_2= ruleBetweenCondition ) )
            // InternalBdd.g:2081:2: (this_StateCondition_0= ruleStateCondition | this_PropCondition_1= rulePropCondition | this_BetweenCondition_2= ruleBetweenCondition )
            {
            // InternalBdd.g:2081:2: (this_StateCondition_0= ruleStateCondition | this_PropCondition_1= rulePropCondition | this_BetweenCondition_2= ruleBetweenCondition )
            int alt20=3;
            int LA20_0 = input.LA(1);

            if ( (LA20_0==RULE_ID) ) {
                int LA20_1 = input.LA(2);

                if ( (LA20_1==33) ) {
                    int LA20_2 = input.LA(3);

                    if ( (LA20_2==RULE_ID) ) {
                        int LA20_4 = input.LA(4);

                        if ( ((LA20_4>=55 && LA20_4<=59)) ) {
                            alt20=2;
                        }
                        else if ( (LA20_4==27) ) {
                            int LA20_6 = input.LA(5);

                            if ( ((LA20_6>=RULE_ID && LA20_6<=RULE_INT)) ) {
                                alt20=2;
                            }
                            else if ( (LA20_6==30) ) {
                                alt20=3;
                            }
                            else {
                                NoViableAltException nvae =
                                    new NoViableAltException("", 20, 6, input);

                                throw nvae;
                            }
                        }
                        else {
                            NoViableAltException nvae =
                                new NoViableAltException("", 20, 4, input);

                            throw nvae;
                        }
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 20, 2, input);

                        throw nvae;
                    }
                }
                else if ( (LA20_1==27) ) {
                    alt20=1;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 20, 1, input);

                    throw nvae;
                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 20, 0, input);

                throw nvae;
            }
            switch (alt20) {
                case 1 :
                    // InternalBdd.g:2082:3: this_StateCondition_0= ruleStateCondition
                    {

                    			newCompositeNode(grammarAccess.getReqCondAccess().getStateConditionParserRuleCall_0());
                    		
                    pushFollow(FOLLOW_2);
                    this_StateCondition_0=ruleStateCondition();

                    state._fsp--;


                    			current = this_StateCondition_0;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 2 :
                    // InternalBdd.g:2091:3: this_PropCondition_1= rulePropCondition
                    {

                    			newCompositeNode(grammarAccess.getReqCondAccess().getPropConditionParserRuleCall_1());
                    		
                    pushFollow(FOLLOW_2);
                    this_PropCondition_1=rulePropCondition();

                    state._fsp--;


                    			current = this_PropCondition_1;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 3 :
                    // InternalBdd.g:2100:3: this_BetweenCondition_2= ruleBetweenCondition
                    {

                    			newCompositeNode(grammarAccess.getReqCondAccess().getBetweenConditionParserRuleCall_2());
                    		
                    pushFollow(FOLLOW_2);
                    this_BetweenCondition_2=ruleBetweenCondition();

                    state._fsp--;


                    			current = this_BetweenCondition_2;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleReqCond"


    // $ANTLR start "ruleTimeUnit"
    // InternalBdd.g:2112:1: ruleTimeUnit returns [Enumerator current=null] : ( (enumLiteral_0= 'milliseconds' ) | (enumLiteral_1= 'seconds' ) | (enumLiteral_2= 'minutes' ) | (enumLiteral_3= 'hours' ) ) ;
    public final Enumerator ruleTimeUnit() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;
        Token enumLiteral_3=null;


        	enterRule();

        try {
            // InternalBdd.g:2118:2: ( ( (enumLiteral_0= 'milliseconds' ) | (enumLiteral_1= 'seconds' ) | (enumLiteral_2= 'minutes' ) | (enumLiteral_3= 'hours' ) ) )
            // InternalBdd.g:2119:2: ( (enumLiteral_0= 'milliseconds' ) | (enumLiteral_1= 'seconds' ) | (enumLiteral_2= 'minutes' ) | (enumLiteral_3= 'hours' ) )
            {
            // InternalBdd.g:2119:2: ( (enumLiteral_0= 'milliseconds' ) | (enumLiteral_1= 'seconds' ) | (enumLiteral_2= 'minutes' ) | (enumLiteral_3= 'hours' ) )
            int alt21=4;
            switch ( input.LA(1) ) {
            case 49:
                {
                alt21=1;
                }
                break;
            case 50:
                {
                alt21=2;
                }
                break;
            case 51:
                {
                alt21=3;
                }
                break;
            case 52:
                {
                alt21=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 21, 0, input);

                throw nvae;
            }

            switch (alt21) {
                case 1 :
                    // InternalBdd.g:2120:3: (enumLiteral_0= 'milliseconds' )
                    {
                    // InternalBdd.g:2120:3: (enumLiteral_0= 'milliseconds' )
                    // InternalBdd.g:2121:4: enumLiteral_0= 'milliseconds'
                    {
                    enumLiteral_0=(Token)match(input,49,FOLLOW_2); 

                    				current = grammarAccess.getTimeUnitAccess().getMILLISECONDSEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getTimeUnitAccess().getMILLISECONDSEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalBdd.g:2128:3: (enumLiteral_1= 'seconds' )
                    {
                    // InternalBdd.g:2128:3: (enumLiteral_1= 'seconds' )
                    // InternalBdd.g:2129:4: enumLiteral_1= 'seconds'
                    {
                    enumLiteral_1=(Token)match(input,50,FOLLOW_2); 

                    				current = grammarAccess.getTimeUnitAccess().getSECONDSEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_1, grammarAccess.getTimeUnitAccess().getSECONDSEnumLiteralDeclaration_1());
                    			

                    }


                    }
                    break;
                case 3 :
                    // InternalBdd.g:2136:3: (enumLiteral_2= 'minutes' )
                    {
                    // InternalBdd.g:2136:3: (enumLiteral_2= 'minutes' )
                    // InternalBdd.g:2137:4: enumLiteral_2= 'minutes'
                    {
                    enumLiteral_2=(Token)match(input,51,FOLLOW_2); 

                    				current = grammarAccess.getTimeUnitAccess().getMINUTESEnumLiteralDeclaration_2().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_2, grammarAccess.getTimeUnitAccess().getMINUTESEnumLiteralDeclaration_2());
                    			

                    }


                    }
                    break;
                case 4 :
                    // InternalBdd.g:2144:3: (enumLiteral_3= 'hours' )
                    {
                    // InternalBdd.g:2144:3: (enumLiteral_3= 'hours' )
                    // InternalBdd.g:2145:4: enumLiteral_3= 'hours'
                    {
                    enumLiteral_3=(Token)match(input,52,FOLLOW_2); 

                    				current = grammarAccess.getTimeUnitAccess().getHOURSEnumLiteralDeclaration_3().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_3, grammarAccess.getTimeUnitAccess().getHOURSEnumLiteralDeclaration_3());
                    			

                    }


                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleTimeUnit"


    // $ANTLR start "ruleDeltaOp"
    // InternalBdd.g:2155:1: ruleDeltaOp returns [Enumerator current=null] : ( (enumLiteral_0= 'increased' ) | (enumLiteral_1= 'decreased' ) ) ;
    public final Enumerator ruleDeltaOp() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;


        	enterRule();

        try {
            // InternalBdd.g:2161:2: ( ( (enumLiteral_0= 'increased' ) | (enumLiteral_1= 'decreased' ) ) )
            // InternalBdd.g:2162:2: ( (enumLiteral_0= 'increased' ) | (enumLiteral_1= 'decreased' ) )
            {
            // InternalBdd.g:2162:2: ( (enumLiteral_0= 'increased' ) | (enumLiteral_1= 'decreased' ) )
            int alt22=2;
            int LA22_0 = input.LA(1);

            if ( (LA22_0==53) ) {
                alt22=1;
            }
            else if ( (LA22_0==54) ) {
                alt22=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 22, 0, input);

                throw nvae;
            }
            switch (alt22) {
                case 1 :
                    // InternalBdd.g:2163:3: (enumLiteral_0= 'increased' )
                    {
                    // InternalBdd.g:2163:3: (enumLiteral_0= 'increased' )
                    // InternalBdd.g:2164:4: enumLiteral_0= 'increased'
                    {
                    enumLiteral_0=(Token)match(input,53,FOLLOW_2); 

                    				current = grammarAccess.getDeltaOpAccess().getINCEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getDeltaOpAccess().getINCEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalBdd.g:2171:3: (enumLiteral_1= 'decreased' )
                    {
                    // InternalBdd.g:2171:3: (enumLiteral_1= 'decreased' )
                    // InternalBdd.g:2172:4: enumLiteral_1= 'decreased'
                    {
                    enumLiteral_1=(Token)match(input,54,FOLLOW_2); 

                    				current = grammarAccess.getDeltaOpAccess().getDECEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_1, grammarAccess.getDeltaOpAccess().getDECEnumLiteralDeclaration_1());
                    			

                    }


                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleDeltaOp"


    // $ANTLR start "ruleComp"
    // InternalBdd.g:2182:1: ruleComp returns [Enumerator current=null] : ( (enumLiteral_0= 'is_greater_or_equal_to' ) | (enumLiteral_1= 'is_less_or_equal_to' ) | (enumLiteral_2= 'is_greater_than' ) | (enumLiteral_3= 'is_less_than' ) | (enumLiteral_4= 'is_not' ) | (enumLiteral_5= 'is' ) ) ;
    public final Enumerator ruleComp() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;
        Token enumLiteral_3=null;
        Token enumLiteral_4=null;
        Token enumLiteral_5=null;


        	enterRule();

        try {
            // InternalBdd.g:2188:2: ( ( (enumLiteral_0= 'is_greater_or_equal_to' ) | (enumLiteral_1= 'is_less_or_equal_to' ) | (enumLiteral_2= 'is_greater_than' ) | (enumLiteral_3= 'is_less_than' ) | (enumLiteral_4= 'is_not' ) | (enumLiteral_5= 'is' ) ) )
            // InternalBdd.g:2189:2: ( (enumLiteral_0= 'is_greater_or_equal_to' ) | (enumLiteral_1= 'is_less_or_equal_to' ) | (enumLiteral_2= 'is_greater_than' ) | (enumLiteral_3= 'is_less_than' ) | (enumLiteral_4= 'is_not' ) | (enumLiteral_5= 'is' ) )
            {
            // InternalBdd.g:2189:2: ( (enumLiteral_0= 'is_greater_or_equal_to' ) | (enumLiteral_1= 'is_less_or_equal_to' ) | (enumLiteral_2= 'is_greater_than' ) | (enumLiteral_3= 'is_less_than' ) | (enumLiteral_4= 'is_not' ) | (enumLiteral_5= 'is' ) )
            int alt23=6;
            switch ( input.LA(1) ) {
            case 55:
                {
                alt23=1;
                }
                break;
            case 56:
                {
                alt23=2;
                }
                break;
            case 57:
                {
                alt23=3;
                }
                break;
            case 58:
                {
                alt23=4;
                }
                break;
            case 59:
                {
                alt23=5;
                }
                break;
            case 27:
                {
                alt23=6;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 23, 0, input);

                throw nvae;
            }

            switch (alt23) {
                case 1 :
                    // InternalBdd.g:2190:3: (enumLiteral_0= 'is_greater_or_equal_to' )
                    {
                    // InternalBdd.g:2190:3: (enumLiteral_0= 'is_greater_or_equal_to' )
                    // InternalBdd.g:2191:4: enumLiteral_0= 'is_greater_or_equal_to'
                    {
                    enumLiteral_0=(Token)match(input,55,FOLLOW_2); 

                    				current = grammarAccess.getCompAccess().getGEQEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getCompAccess().getGEQEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalBdd.g:2198:3: (enumLiteral_1= 'is_less_or_equal_to' )
                    {
                    // InternalBdd.g:2198:3: (enumLiteral_1= 'is_less_or_equal_to' )
                    // InternalBdd.g:2199:4: enumLiteral_1= 'is_less_or_equal_to'
                    {
                    enumLiteral_1=(Token)match(input,56,FOLLOW_2); 

                    				current = grammarAccess.getCompAccess().getLEQEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_1, grammarAccess.getCompAccess().getLEQEnumLiteralDeclaration_1());
                    			

                    }


                    }
                    break;
                case 3 :
                    // InternalBdd.g:2206:3: (enumLiteral_2= 'is_greater_than' )
                    {
                    // InternalBdd.g:2206:3: (enumLiteral_2= 'is_greater_than' )
                    // InternalBdd.g:2207:4: enumLiteral_2= 'is_greater_than'
                    {
                    enumLiteral_2=(Token)match(input,57,FOLLOW_2); 

                    				current = grammarAccess.getCompAccess().getGTEnumLiteralDeclaration_2().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_2, grammarAccess.getCompAccess().getGTEnumLiteralDeclaration_2());
                    			

                    }


                    }
                    break;
                case 4 :
                    // InternalBdd.g:2214:3: (enumLiteral_3= 'is_less_than' )
                    {
                    // InternalBdd.g:2214:3: (enumLiteral_3= 'is_less_than' )
                    // InternalBdd.g:2215:4: enumLiteral_3= 'is_less_than'
                    {
                    enumLiteral_3=(Token)match(input,58,FOLLOW_2); 

                    				current = grammarAccess.getCompAccess().getLTEnumLiteralDeclaration_3().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_3, grammarAccess.getCompAccess().getLTEnumLiteralDeclaration_3());
                    			

                    }


                    }
                    break;
                case 5 :
                    // InternalBdd.g:2222:3: (enumLiteral_4= 'is_not' )
                    {
                    // InternalBdd.g:2222:3: (enumLiteral_4= 'is_not' )
                    // InternalBdd.g:2223:4: enumLiteral_4= 'is_not'
                    {
                    enumLiteral_4=(Token)match(input,59,FOLLOW_2); 

                    				current = grammarAccess.getCompAccess().getNEQEnumLiteralDeclaration_4().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_4, grammarAccess.getCompAccess().getNEQEnumLiteralDeclaration_4());
                    			

                    }


                    }
                    break;
                case 6 :
                    // InternalBdd.g:2230:3: (enumLiteral_5= 'is' )
                    {
                    // InternalBdd.g:2230:3: (enumLiteral_5= 'is' )
                    // InternalBdd.g:2231:4: enumLiteral_5= 'is'
                    {
                    enumLiteral_5=(Token)match(input,27,FOLLOW_2); 

                    				current = grammarAccess.getCompAccess().getEQEnumLiteralDeclaration_5().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_5, grammarAccess.getCompAccess().getEQEnumLiteralDeclaration_5());
                    			

                    }


                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleComp"

    // Delegated rules


    protected DFA10 dfa10 = new DFA10(this);
    static final String dfa_1s = "\13\uffff";
    static final String dfa_2s = "\10\uffff\1\12\2\uffff";
    static final String dfa_3s = "\1\4\1\61\1\uffff\4\30\1\31\1\24\2\uffff";
    static final String dfa_4s = "\1\5\1\64\1\uffff\4\30\1\31\1\25\2\uffff";
    static final String dfa_5s = "\2\uffff\1\3\6\uffff\1\1\1\2";
    static final String dfa_6s = "\13\uffff}>";
    static final String[] dfa_7s = {
            "\1\2\1\1",
            "\1\3\1\4\1\5\1\6",
            "",
            "\1\7",
            "\1\7",
            "\1\7",
            "\1\7",
            "\1\10",
            "\1\11\1\12",
            "",
            ""
    };

    static final short[] dfa_1 = DFA.unpackEncodedString(dfa_1s);
    static final short[] dfa_2 = DFA.unpackEncodedString(dfa_2s);
    static final char[] dfa_3 = DFA.unpackEncodedStringToUnsignedChars(dfa_3s);
    static final char[] dfa_4 = DFA.unpackEncodedStringToUnsignedChars(dfa_4s);
    static final short[] dfa_5 = DFA.unpackEncodedString(dfa_5s);
    static final short[] dfa_6 = DFA.unpackEncodedString(dfa_6s);
    static final short[][] dfa_7 = unpackEncodedStringArray(dfa_7s);

    class DFA10 extends DFA {

        public DFA10(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 10;
            this.eot = dfa_1;
            this.eof = dfa_2;
            this.min = dfa_3;
            this.max = dfa_4;
            this.accept = dfa_5;
            this.special = dfa_6;
            this.transition = dfa_7;
        }
        public String getDescription() {
            return "644:2: ( ( ( (lv_time_0_0= ruleTimeEvent ) ) otherlv_1= 'and' ( (lv_end_2_0= ruleEndEvent ) ) ) | ( (lv_time_3_0= ruleTimeEvent ) ) | ( (lv_end_4_0= ruleEndEvent ) ) )";
        }
    }
 

    public static final BitSet FOLLOW_1 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_2 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_3 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_4 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_5 = new BitSet(new long[]{0x0000000000041000L});
    public static final BitSet FOLLOW_6 = new BitSet(new long[]{0x0000000400040002L});
    public static final BitSet FOLLOW_7 = new BitSet(new long[]{0x0000000400000002L});
    public static final BitSet FOLLOW_8 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_9 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_10 = new BitSet(new long[]{0x0000000000018010L});
    public static final BitSet FOLLOW_11 = new BitSet(new long[]{0x0000000000010010L});
    public static final BitSet FOLLOW_12 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_13 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_14 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_15 = new BitSet(new long[]{0x0000000000D00000L});
    public static final BitSet FOLLOW_16 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_17 = new BitSet(new long[]{0x0000000000100002L});
    public static final BitSet FOLLOW_18 = new BitSet(new long[]{0x0000000000000030L});
    public static final BitSet FOLLOW_19 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_20 = new BitSet(new long[]{0x001E000000000000L});
    public static final BitSet FOLLOW_21 = new BitSet(new long[]{0x0000000001000000L});
    public static final BitSet FOLLOW_22 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_23 = new BitSet(new long[]{0x0000000004000000L});
    public static final BitSet FOLLOW_24 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_25 = new BitSet(new long[]{0x0000000030000010L});
    public static final BitSet FOLLOW_26 = new BitSet(new long[]{0x0000000020000010L});
    public static final BitSet FOLLOW_27 = new BitSet(new long[]{0x0F80000008000000L});
    public static final BitSet FOLLOW_28 = new BitSet(new long[]{0x0000000040000000L});
    public static final BitSet FOLLOW_29 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_30 = new BitSet(new long[]{0x0060000000000000L});
    public static final BitSet FOLLOW_31 = new BitSet(new long[]{0x0000000100000000L});
    public static final BitSet FOLLOW_32 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_33 = new BitSet(new long[]{0x0000000800000000L});
    public static final BitSet FOLLOW_34 = new BitSet(new long[]{0x0000001000000000L});
    public static final BitSet FOLLOW_35 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_36 = new BitSet(new long[]{0x000000C000000000L});
    public static final BitSet FOLLOW_37 = new BitSet(new long[]{0x0000004000000000L});
    public static final BitSet FOLLOW_38 = new BitSet(new long[]{0x0000030800000000L});
    public static final BitSet FOLLOW_39 = new BitSet(new long[]{0x00000C0000000000L});
    public static final BitSet FOLLOW_40 = new BitSet(new long[]{0x0000100000000000L});
    public static final BitSet FOLLOW_41 = new BitSet(new long[]{0x0000200000000000L});
    public static final BitSet FOLLOW_42 = new BitSet(new long[]{0x0000400000000000L});
    public static final BitSet FOLLOW_43 = new BitSet(new long[]{0x0000800000000000L});
    public static final BitSet FOLLOW_44 = new BitSet(new long[]{0x0001000000000000L});

}